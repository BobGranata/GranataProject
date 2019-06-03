//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscPresenterDocList.h"
#include "UView.h"
#include "uResendLetter.h"
#include <Vcl.Dialogs.hpp>
#include <memory>
//---------------------------------------------------------------------------

lscPresenterDocList::lscPresenterDocList(lsiListColumnView *view) : m_View(view)
{
	m_InfComDll = NULL;
}
//---------------------------------------------------------------------------
CInfComDll *lscPresenterDocList::getPrepareInfComDll()
{
	CEngine *m_dbEngine = CSingleEngine::Get();

	CInfCom *InfCom = m_extendDocCircul->infCom;
	CRole *Role = m_extendDocCircul->role;
	CDocCircul *DocCircul = m_extendDocCircul->docCircul;

	String icType = InfCom->GetType();
	if (icType.IsEmpty())
	{
		ShowMessage("Ошибка. Не получается узнать тип направления.");
		//нужно ошибку вернуть
        return NULL;
	}

	CInfComCollect *InfComCollect = m_dbEngine->InfComDb->GetInfComCollectByType(icType);


	CInfComDll *InfComDll = InfComCollect->InfComDll;
	int Number = -1;

	// Очищаем dll и заполняем её ролями, ИВ и т.п.
	for (int i = 0; i < InfComCollect->GetCount(); i++)
	{
		if (InfComCollect->GetInfCom(i) == InfCom)
		{
			Number = i;
			break;
		}
	}

	if (!m_dbEngine->FindRoles(InfComCollect,Number))
	{
//		lsMessageDlg(m_dbEngine->LastErrString, "", mtError);
		return NULL;
	}

	m_dbEngine->CurrentDocCircul = DocCircul;
	if (!m_dbEngine->SaveDocCirculToDll())
	{
//		lsMessageDlg(m_dbEngine->LastErrString, "", mtError);
		return NULL;
	}

	return InfComDll;
}
//---------------------------------------------------------------------------
void lscPresenterDocList::viewDocument()
{
	int indexDoc = m_View->getDocumentIndex();
	if (indexDoc == -1)
	{
		indexDoc = 0;
	}

	CInfComDll *InfComDll = m_InfComDll;

	TfmView *viewDc = new TfmView(NULL);
	HWND k = viewDc->Handle;
	InfComDll->ViewDocument(&k, indexDoc);

	viewDc->InfComDll = InfComDll;
	viewDc->ShowModal();
	InfComDll->CloseView();
	delete viewDc;
}

//---------------------------------------------------------------------------
void lscPresenterDocList::fillDocumentList(lscExtendDocCircul *extendDocCircul)
{
	m_extendDocCircul = extendDocCircul;
	m_InfComDll = this->getPrepareInfComDll();
	if (m_InfComDll != NULL)
	{
		m_View->clearList();
		m_InfComDll->fillDocumentList(m_View);
	}
	else
	{
		ShowMessage("Ошибка. Загрузка документооборота прошла неудачно.");
	}
}

//---------------------------------------------------------------------------
void lscPresenterDocList::resendPack()
{
	int indexDoc = m_View->getDocumentIndex();
	if (indexDoc == -1)
	{
		indexDoc = 0;
	}

	CInfComDll *InfComDll = m_InfComDll;

	std::unique_ptr<TfResendLetter> fResendLetter(new TfResendLetter(NULL));
	std::unique_ptr<TStringList> Letter(new TStringList());

	InfComDll->GetLetter(indexDoc, Letter.get());

	fResendLetter.get()->Letter = Letter.get();
	fResendLetter.get()->mmLetterOut->Text = Letter->Text;
	fResendLetter.get()->ShowModal();
}

//---------------------------------------------------------------------------

#pragma package(smart_init)
