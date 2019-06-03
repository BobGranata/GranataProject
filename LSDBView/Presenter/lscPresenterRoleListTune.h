//---------------------------------------------------------------------------

#ifndef lscPresenterRoleListTuneH
#define lscPresenterRoleListTuneH

#include "CEngine.h"
#include "lsiViewRoleListTune.h"
#include "lscModelRoleListTune.h"
//---------------------------------------------------------------------------

class lscPresenterRoleListTune {
	private:
	CSetOrder *Params;
	CEngine *Engine;

	lsiViewRoleListTune *m_View;
	lscModelRoleListTune *m_Model;

	public:
	void __fastcall fillRoleList();

	void __fastcall listRoleTypeClick();

	void __fastcall nextPage();
	void __fastcall previousPage();
	void __fastcall setPage();

	void __fastcall btnSearchClick();
	void __fastcall roleListClickCheck();
	void __fastcall editRole();
	void __fastcall addRole();

	void __fastcall clearRoleList();
	void __fastcall fillRoleListColumn();

	void setFilterProperty();
	void setFilterText(String text);

	void visiParamEdit();

	lscPresenterRoleListTune(lsiViewRoleListTune *ViewRoleListTune);
	~lscPresenterRoleListTune();

	void __fastcall init(CEngine *Engine, CSetOrder *Params);
};

#endif
