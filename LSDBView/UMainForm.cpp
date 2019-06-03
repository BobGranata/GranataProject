//---------------------------------------------------------------------------
#pragma hdrstop

#include "UMainForm.h"
#include "Code.h"
#include "Check.h"
#include "CCommon.h"
#include "CCertList.h"
#include "uInterface.h"
#include "UReport.h"
#include "UReportRate.h"
#include "UReportEnterpriseSummary.h"
#include "UReportCertList.h"
#include "UReportCertListPFR.h"
#include "UReportEnterpriseSummaryPfr.h"
#include "UReportListPfrRegistr.h"
#include "UExportError.h"
#include "UCertDownload.h"
#include "UChoiceBase.h"
#include "uLoadingWait.h"
#include "lsParams.h"
#include "paramsProxy.h"

//---------------------------------------------------------------------------
#pragma resource "*.dfm"

TfrmMain *frmMain;

//---------------------------------------------------------------------------
__fastcall TfrmMain::TfrmMain(TComponent* Owner)
	: TForm(Owner), isActivated(false)
{
	frmDocumentList = new TfrmDocumentList(this->panelDocumentList);
	frmDocumentList->Parent = this->panelDocumentList;
	frmDocumentList->Show();

	m_PresenterMain = new lscPresenterMain(this, frmDocumentList, this, this, this);
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::N4Click(TObject *Sender)
{
	Application->Terminate();
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::N22Click(TObject *Sender)
{
	//
	try
	{
		String s = "\"" + ExtractFilePath(Application->ExeName) +
			"HELP\\Manual.chm\"";

		ShellExecute(NULL,NULL,s.w_str(),NULL,NULL,SW_SHOW);
	} catch (...)
	{
	}
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::btnRefreshDBClick(TObject *Sender)
{
	m_PresenterMain->updateDB();
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::N2Click(TObject *Sender)
{
//	FormInterface->Params = Params;
//	FormInterface->Engine = Engine;
//	if (FormInterface->ShowModal() == mrOk) {
//		Params->SaveToFile(
//			ExtractFilePath(Application->ExeName) + "Params.ini","=",true);
//	}
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::N24Click(TObject *Sender)
{
	formReport->Show();
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::N14Click(TObject *Sender)
{
	FormReportEnterpriseSummary->Show();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::N11Click(TObject *Sender)
{
	//
   FormReportCertList->ShowModal();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::N7Click(TObject *Sender)
{
	FormReportCertListPFR->ShowModal();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::N15Click(TObject *Sender)
{
	FormReportEnterpriseSummaryPfr->Show();
}
//---------------------------------------------------------------------------


void __fastcall TfrmMain::N25Click(TObject *Sender)
{
	CertDownload->ShowModal();
}
//---------------------------------------------------------------------------


void __fastcall TfrmMain::N31Click(TObject *Sender)
{
	fmExportError->ShowModal();
}

//---------------------------------------------------------------------------
void __fastcall TfrmMain::NListPfrRegistrationClick(TObject *Sender)
{
	FormReportListPfrRegistr->ShowModal();
}
//---------------------------------------------------------------------------


void __fastcall TfrmMain::N32Click(TObject *Sender)
{
	formReportRate->Show();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::btnOpenBaseClick(TObject *Sender)
{
	m_PresenterMain->init();
}
//---------------------------------------------------------------------------
void __fastcall TfrmMain::btnFindDocCirculClick(TObject *Sender)
{
	m_PresenterMain->findDocCirculClick();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::FormActivate(TObject *Sender)
{
	if (!isActivated)
	{
		m_PresenterMain->init();
		isActivated = true;
	}
}
//---------------------------------------------------------------------------
int TfrmMain::addItemDocCirculList()
{
	TListItem *item = lvDocCirculList->Items->Add();

	for (int i = 0; i < lvDocCirculList->Columns->Count; i++)
	{
		item->SubItems->Add("");
	}
}
//---------------------------------------------------------------------------
void TfrmMain::addValueToDocCirculList(int row, int column, String text)
{
	if (row < lvDocCirculList->Items->Count)
	{
		if (column == 0)
		{
			lvDocCirculList->Items->Item[row]->Caption = text;
		}
		else
		{
			lvDocCirculList->Items->Item[row]->SubItems->Strings[column-1] = text;
		}
	}
}
//---------------------------------------------------------------------------
void TfrmMain::addColumn(String columnCaption)
{
	TListColumn *listColumn = lvDocCirculList->Columns->Add();
	listColumn->Caption = columnCaption;
}
//---------------------------------------------------------------------------

void TfrmMain::addStatusImage(int itemIndex, int imageIndex)
{
	lvDocCirculList->Items->Item[itemIndex]->ImageIndex = imageIndex;
}
//---------------------------------------------------------------------------

void TfrmMain::setColumnSize(int indexColumn, String text)
{
	if (indexColumn == lvDocCirculList->Columns->Count - 1)
	{
		int allColWidth = lvDocCirculList->Width;
		for (int i = 0; i < lvDocCirculList->Columns->Count - 1; i++) {
			allColWidth -= lvDocCirculList->Columns->Items[i]->Width;
		}
		lvDocCirculList->Columns->Items[indexColumn]->Width = allColWidth - 30;
		return;
	}
	lvDocCirculList->Columns->Items[indexColumn]->Width = lvDocCirculList->Canvas->TextWidth(text) + 20;
}
//---------------------------------------------------------------------------
void TfrmMain::clearExtendDcList()
{
	lvDocCirculList->Clear();
}
//---------------------------------------------------------------------------
void TfrmMain::addRoleFilterParam(String param)
{
	cbSearchField->Items->Add(param);
	cbSearchField->ItemIndex = 0;
}
//---------------------------------------------------------------------------
String TfrmMain::getRoleFilterText()
{
	return edSearchValue->Text;
}
//---------------------------------------------------------------------------
int TfrmMain::getRoleFilterParam()
{
	return cbSearchField->ItemIndex;
}
//---------------------------------------------------------------------------

String TfrmMain::getDcFilterText()
{
	return edDcParamText->Text;
}
//---------------------------------------------------------------------------

int TfrmMain::getDcFilterParam()
{
	return cbDcParam->ItemIndex;
}
//---------------------------------------------------------------------------

void TfrmMain::addDcFilterParam(String param)
{
	cbDcParam->Items->Add(param);
	cbDcParam->ItemIndex = 0;
}
//---------------------------------------------------------------------------

String TfrmMain::getInfComFilterText()
{
	return edInfComParam->Text;
}
//---------------------------------------------------------------------------

int TfrmMain::getInfComFilterParam()
{
	return cbInfComParam->ItemIndex;
}
//---------------------------------------------------------------------------

void TfrmMain::addInfComFilterParam(String param)
{
	cbInfComParam->Items->Add(param);
	cbInfComParam->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void TfrmMain::addDateFilterParam(String param)
{
	cbDatePeriodFilter->Items->Add(param);
	cbDatePeriodFilter->ItemIndex = 0;
}
//---------------------------------------------------------------------------
int TfrmMain::getDateFilterParam()
{
	return cbDatePeriodFilter->ItemIndex;
}
//---------------------------------------------------------------------------
void TfrmMain::setBeginDate(TDateTime date)
{
	dtpBeginDate->Date = date;
}
//---------------------------------------------------------------------------
void TfrmMain::setEndDate(TDateTime date)
{
	dtpEndDate->Date = date;
}
//---------------------------------------------------------------------------
TDateTime TfrmMain::getBeginDate()
{
	return dtpBeginDate->Date;
}
//---------------------------------------------------------------------------
TDateTime TfrmMain::getEndDate()
{
	return dtpEndDate->Date;
}
//---------------------------------------------------------------------------
void __fastcall TfrmMain::cbDatePeriodFilterChange(TObject *Sender)
{
	m_PresenterMain->datePeriodFilterChange();
}
//---------------------------------------------------------------------------
void TfrmMain::addDcTypeFilterParam(String param)
{
	cbDocCirculType->Items->Add(param);
}

//---------------------------------------------------------------------------
void TfrmMain::addDcStatusFilterParam(String param)
{
	cbDocCirculStatus->Items->Add(param);
}

//---------------------------------------------------------------------------
int TfrmMain::getDcStatusFilterParam()
{
	return cbDocCirculStatus->ItemIndex;
}

//---------------------------------------------------------------------------
void TfrmMain::addDocumentStatusFilterParam(String param)
{
	cbDocumentStatus->Items->Add(param);
}

//---------------------------------------------------------------------------

int TfrmMain::getDocumentStatusFilterParam()
{
	return cbDocumentStatus->ItemIndex;
}
//---------------------------------------------------------------------------

String TfrmMain::getDocumentFilterText()
{
	return cbDocumentParamText->Text;
}
//---------------------------------------------------------------------------

int TfrmMain::getDocumentFilterParam()
{
	return cbDocumentParam->ItemIndex;
}
//---------------------------------------------------------------------------

void TfrmMain::addDocumentFilterParam(String param)
{
	cbDocumentParam->Items->Add(param);
	cbDocumentParam->ItemIndex = 0;
}

//---------------------------------------------------------------------------
void TfrmMain::clearFilter()
{
	// даты
	cbDatePeriodFilter->Clear();
	//
	cbDcParam->Clear();
	cbInfComParam->Clear();
	cbSearchField->Clear();
	cbDocCirculType->Clear();
	cbDocCirculStatus->Clear();

    lvDocCirculList->Columns->Clear();
	lvDocCirculList->Clear();

	cbPageOnScreen->Clear();
}

//---------------------------------------------------------------------------
int TfrmMain::getDcTypeFilterParam()
{
	return cbDocCirculType->ItemIndex;
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::lvDocCirculListDblClick(TObject *Sender)
{
//	m_PresenterMain->loadDocCircul();
}
//---------------------------------------------------------------------------
int TfrmMain::getExtDocCirculIndex()
{
	return lvDocCirculList->ItemIndex;
}
//---------------------------------------------------------------------------
int TfrmMain::getPaggingLimitIndex()
{
	return cbPageOnScreen->ItemIndex;
}
//---------------------------------------------------------------------------
void TfrmMain::setPaggingLimit(int page)
{
	cbPageOnScreen->ItemIndex = page;
}
//---------------------------------------------------------------------------
void TfrmMain::addPaggingLimit(int limit)
{
	cbPageOnScreen->Items->Add(IntToStr(limit));
}
//---------------------------------------------------------------------------
int TfrmMain::getPage()
{
	return StrToInt(edCurrentPage->Text);
}
//---------------------------------------------------------------------------
void TfrmMain::setPage(int page)
{
    edCurrentPage->Text = IntToStr(page);
}
//---------------------------------------------------------------------------
void TfrmMain::setPageCount(int page)
{
    lbPageCount->Caption = "\\ " + IntToStr(page);
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::edCurrentPageClick(TObject *Sender)
{
    //
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::btnNextPageClick(TObject *Sender)
{
    m_PresenterMain->nextPage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::btnLastPageClick(TObject *Sender)
{
	m_PresenterMain->lastPage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::edCurrentPageChange(TObject *Sender)
{
//    m_PresenterMain->changePage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::cbPageOnScreenChange(TObject *Sender)
{
    m_PresenterMain->changeLimit();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::btnPreviousPageClick(TObject *Sender)
{
	m_PresenterMain->previousPage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::btnFirstPageClick(TObject *Sender)
{
	m_PresenterMain->firstPage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::lvDocCirculListCustomDrawItem(TCustomListView *Sender, TListItem *Item,
          TCustomDrawState State, bool &DefaultDraw)
{
	if ((Item->Index % 2) != 0)
	{
		Sender->Canvas->Brush->Color = (TColor)RGB(235, 235, 235);
	}
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::lvDocCirculListCustomDrawSubItem(TCustomListView *Sender,
          TListItem *Item, int SubItem, TCustomDrawState State, bool &DefaultDraw)
{
//	Sender->Canvas->Brush->Color = (TColor)RGB(200, 235, 100);
}

//---------------------------------------------------------------------------
void TfrmMain::showLoading()
{
	frmLoadingWait->Show();
}

//---------------------------------------------------------------------------
void TfrmMain::hideLoading()
{
	frmLoadingWait->Hide();
}
//---------------------------------------------------------------------------


void __fastcall TfrmMain::lvDocCirculListClick(TObject *Sender)
{
	m_PresenterMain->loadDocumentList();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::lvDocCirculListKeyDown(TObject *Sender, WORD &Key, TShiftState Shift)

{
	m_PresenterMain->loadDocumentList();
}
//---------------------------------------------------------------------------

void __fastcall TfrmMain::lvDocCirculListKeyUp(TObject *Sender, WORD &Key, TShiftState Shift)

{
	m_PresenterMain->loadDocumentList();
}
//---------------------------------------------------------------------------


