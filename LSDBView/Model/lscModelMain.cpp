//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscModelMain.h"
#include "CCommon.h"
#include <Vcl.Dialogs.hpp>
//---------------------------------------------------------------------------

lscModelMain::lscModelMain() : m_paging()
{
    m_findDocCirculList = NULL;
	// Загрузка движка.
	m_dbEngine = CSingleEngine::Get(CCommon::GetRoleScope(), CCommon::GetProgName());

	m_programParams = CSingleParams::Get();
	try
	{
		String strParamIniPath = ExtractFilePath(Application->ExeName) + "Params.ini";
		if (!m_programParams->LoadFromFile(strParamIniPath, "=", true))
		{
				m_programParams->SaveToFile(strParamIniPath, "=", true);
		}
	} catch (...)
	{
	}

}
//---------------------------------------------------------------------------
lscModelMain::~lscModelMain()
{
	//
}

//---------------------------------------------------------------------------
void __fastcall lscModelMain::init(String basePath)
{
	m_dbEngine->FullInitByPath(ExtractFilePath(Application->ExeName)+"DbEn\\DbRef.dll", basePath);

	m_columnDocCirculList.clear();
    m_paging.clear();

	m_columnDocCirculList.push_back(lscColumnMainList(eColumnType::DC, "SendedDate"));
	m_columnDocCirculList.push_back(lscColumnMainList(eColumnType::DC, "Name"));
	m_columnDocCirculList.push_back(lscColumnMainList(eColumnType::DC, "DocCirculID"));
	m_columnDocCirculList.push_back(lscColumnMainList(eColumnType::R,  "Name"));
	m_columnDocCirculList.push_back(lscColumnMainList(eColumnType::R,  "INN"));
	m_columnDocCirculList.push_back(lscColumnMainList(eColumnType::IC, "Name"));
}

//---------------------------------------------------------------------------
void __fastcall lscModelMain::update()
{
	m_dbEngine->updateDB();
}

//---------------------------------------------------------------------------
lscModelPaging *lscModelMain::getPaging()
{
    return &m_paging;
}

//---------------------------------------------------------------------------
void lscModelMain::clearDocCirculList()
{
	if (m_findDocCirculList != NULL)
	{
//		for_each(m_findDocCirculList
		for (vector<lscExtendDocCircul*>::iterator itExt = m_findDocCirculList->begin(); itExt != m_findDocCirculList->end(); itExt++)
		{
			delete *itExt;
		}

	    delete m_findDocCirculList;
	}
}

//---------------------------------------------------------------------------
void __fastcall lscModelMain::findDocCircul(CFilter *_filter)
{
	this->clearDocCirculList();
	lscInteractorExtendDocCircul InteractorExtendDocCircul;
	m_findDocCirculList = InteractorExtendDocCircul.getExtendDocCirculList(m_dbEngine, _filter);

    m_paging.calcPageCount(m_findDocCirculList->size());
}

//---------------------------------------------------------------------------
int lscModelMain::getDocCirculListColumnCount()
{
	return m_columnDocCirculList.size();
}
//---------------------------------------------------------------------------
lscColumnMainList *lscModelMain::getDocCirculColumnParams(int index)
{
	if (index < m_columnDocCirculList.size())
		return &m_columnDocCirculList[index];
	else
		return NULL;
}
//---------------------------------------------------------------------------
int lscModelMain::getDocCirculCount()
{
	if (m_findDocCirculList != NULL)
		return m_findDocCirculList->size();
	else
        return 0;
}
//---------------------------------------------------------------------------
lscExtendDocCircul *lscModelMain::getDocCirculListItem(int index)
{
	return m_findDocCirculList->at(index);
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
