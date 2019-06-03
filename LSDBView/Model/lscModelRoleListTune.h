//---------------------------------------------------------------------------

#ifndef lscModelRoleListTuneH
#define lscModelRoleListTuneH

#include "CEngine.h"

//---------------------------------------------------------------------------

class lscModelRoleListTune {
	private:
	vector<String> m_roleCollectTypeList;
	vector<CRole *> m_roleList;

	vector<String> m_visiParams;

	CEngine *m_dbEngine;
	CSetOrder *m_programParams;

	String m_filterProperty;
	String m_filterText;

	public:
	lscModelRoleListTune(CEngine *dbEngine, CSetOrder *programParams);
	~lscModelRoleListTune();

	// todo: del
	int m_offset;
	int m_limit;

	int getVisiParamsCount();
	String getVisiParams(int index);
	void loadVisiParams();
//	void deleteAllVisiParams();

	int currentRoleType;
	int __fastcall getRoleCollectTypeCount();
	String __fastcall getRoleCollectTypeName(int index);

	int __fastcall getRoleListCount();
	CRole *__fastcall getRoleListItem(int index);

	void __fastcall fillRoleList();
	void __fastcall clearRoleList();

	void setFilterProperty(String property);
	void setFilterProperty(int index);
	void setFilterText(String text);

	int getPageCount();
};

#endif
