//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscModelRoleListTune.h"
//---------------------------------------------------------------------------

lscModelRoleListTune::lscModelRoleListTune(CEngine *dbEngine, CSetOrder *programParams)
											: m_dbEngine(dbEngine), m_programParams(programParams)
											, m_offset(0), m_limit(50)
{
}
//---------------------------------------------------------------------------
lscModelRoleListTune::~lscModelRoleListTune()
{
}
//---------------------------------------------------------------------------
void lscModelRoleListTune::loadVisiParams()
{
	m_visiParams.clear();
	TStringList *visiParams = new TStringList();
	String path = ExtractFilePath(Application->ExeName) + "Lists\\VisiRoleListParams.txt";
	if (!FileExists(path)) {
		visiParams->Add("Name");
		visiParams->Add("INN");
		visiParams->SaveToFile(path);
	} else {
		visiParams->LoadFromFile(path);
	}

	for (int i = 0; i < visiParams->Count; i++)
	{
		m_visiParams.push_back(visiParams->Strings[i]);
	}
}
//---------------------------------------------------------------------------
//void lscModelRoleListTune::deleteAllVisiParams()
//{
//	m_visiParams.clear();
//}
//---------------------------------------------------------------------------
int lscModelRoleListTune::getVisiParamsCount()
{
	return m_visiParams.size();
}
//---------------------------------------------------------------------------
String lscModelRoleListTune::getVisiParams(int index)
{
	if (index < m_visiParams.size())
		return m_visiParams[index];
	else
		return "";
}

//---------------------------------------------------------------------------
int __fastcall lscModelRoleListTune::getRoleCollectTypeCount()
{
	if (m_dbEngine != NULL) {
		return m_dbEngine->RoleDb->GetCount();
	} else {
		return 0;
	}
}

//---------------------------------------------------------------------------
String __fastcall lscModelRoleListTune::getRoleCollectTypeName(int index)
{
	CRoleCollect *RoleCollect = m_dbEngine->RoleDb->GetRoleCollect(index);
	if (RoleCollect != NULL) {
		return RoleCollect->RoleCollectDll->GetLocalRolesType();
	} else {
		return "";
	}
}

//---------------------------------------------------------------------------
int __fastcall lscModelRoleListTune::getRoleListCount()
{
	return m_roleList.size();
}

//---------------------------------------------------------------------------
CRole *__fastcall lscModelRoleListTune::getRoleListItem(int index)
{
	return m_roleList.at(index);
}

//---------------------------------------------------------------------------
void __fastcall lscModelRoleListTune::clearRoleList()
{
	m_roleList.clear();
}

//---------------------------------------------------------------------------
void __fastcall lscModelRoleListTune::fillRoleList()
{
	if (m_dbEngine != NULL)
	{
		CRoleCollect *RoleCollect = m_dbEngine->RoleDb->GetRoleCollect(currentRoleType);

		for (int i = 0; i < RoleCollect->GetCount(); i++)
		{
			CRole *role = RoleCollect->GetRole(i);

			String roleProperty = MyLowerCase(role->GetValue(m_filterProperty));
			String searchText = MyLowerCase(m_filterText);
			if (roleProperty.Pos(searchText) || searchText == "")
			{
				m_roleList.push_back(role);
			}
			else
			{
				continue;
			}
		}
	}
}

//---------------------------------------------------------------------------
void lscModelRoleListTune::setFilterProperty(String property)
{
	m_filterProperty = property;
}
//---------------------------------------------------------------------------
void lscModelRoleListTune::setFilterProperty(int index)
{
	m_filterProperty = m_visiParams[index];
}
//---------------------------------------------------------------------------
void lscModelRoleListTune::setFilterText(String text)
{
	m_filterText = text;
}
//---------------------------------------------------------------------------
int lscModelRoleListTune::getPageCount()
{
	int roleCount = m_roleList.size();
	if (roleCount == 0)
	{
		return 0;
	}
	else
	{
		int pageCount = m_roleList.size()/m_limit;
		if (m_roleList.size()%m_limit > 0)
		{
			pageCount++;
		}
		return pageCount;
	}
}
//---------------------------------------------------------------------------
#pragma package(smart_init)
