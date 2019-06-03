//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscPresenterRoleListTune.h"
#include "uSaveParamRoleList.h"
#include "URoleAndInfComTune.h"
#include "URoleTune.h"
//---------------------------------------------------------------------------

lscPresenterRoleListTune::lscPresenterRoleListTune(lsiViewRoleListTune *ViewRoleListTune)
				: m_View(ViewRoleListTune)
{
	//
}

lscPresenterRoleListTune::~lscPresenterRoleListTune()
{
	if (m_Model != NULL)
	{
		delete m_Model;
		m_Model = NULL;
	}
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::init(CEngine *Engine, CSetOrder *Params)
{
	m_Model = new lscModelRoleListTune(Engine, Params);

	this->Params = Params;
	this->Engine = Engine;

	m_Model->loadVisiParams();

	if (Engine != NULL)
	{
		// Настройка организаций и связей.
		m_View->clearRoleTypeList();

		for(int i = 0; i < m_Model->getRoleCollectTypeCount(); i++)
		{
			m_View->addItemRoleTypeList(m_Model->getRoleCollectTypeName(i), i);
		}

		this->fillRoleListColumn();
	}

	m_View->setCurrentRoleType(0);
	m_View->setSearchType(0);

	this->setFilterProperty();
	this->fillRoleList();
}
//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::fillRoleListColumn()
{
	for (int i = 0; i < m_Model->getVisiParamsCount(); i++)
	{
		String value = m_Model->getVisiParams(i);
		m_View->addFindParams(value, i);
		m_View->addColumn(value, i);
	}
}
//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::btnSearchClick()
{
//	if (edSearch->Text == "") return;
//	Screen->Cursor = crHourGlass;
//
//	String Property = "";
//	if (comboSearch->ItemIndex == 0) {
//		Property = "Name";
//   } else if (comboSearch->ItemIndex == 1) {
//		Property = "INN";
//   } else if (comboSearch->ItemIndex == 2) {
//		Property = "Code";
//   } else if (comboSearch->ItemIndex == 3) {
//		Property = "";
//   } else {
//      return;
//   }
//
//	int Selected = -1;
//	if (roleList->ItemIndex != -1) {
//		CRole * Role = (CRole *)roleList->Items->Objects[roleList->ItemIndex];
//		if (Property != "") {
//			String RealValue = Role->GetValue(Property);
//	      RealValue = MyLowerCase(RealValue);
//			String Value = MyLowerCase(edSearch->Text);
//			if (RealValue.Pos(Value) != 0) {
//				Selected = roleList->ItemIndex;
//	      }
//      } else {
//         for (int k = 0; k < Role->GetCount(); k++) {
//         	String t1 = Role->GetField(k);
//            String RealValue = Role->GetValue(t1);
//				RealValue = MyLowerCase(RealValue);
//				String Value = MyLowerCase(edSearch->Text);
//				if (RealValue.Pos(Value) != 0) {
//					Selected = roleList->ItemIndex;
//					break;
//		      }
//         }
//      }
//   }
//
//	bool Cont = false;
//
//	bool res = false;
//
//	for (int i = 0; i < roleList->Count; i++) {
//
//		if (Selected != -1) {
//        	if (Selected == i) {
//				Cont = true;
//				continue;
//			}
//			if (!Cont) {
//				continue;
//			}
//      }
//
//      CRole * Role = (CRole *)roleList->Items->Objects[i];
//	  if (Property != "") {
//			String RealValue = Role->GetValue(Property);
//	      RealValue = MyLowerCase(RealValue);
//			String Value = MyLowerCase(edSearch->Text);
//			if (RealValue.Pos(Value) != 0) {
//				roleList->ItemIndex = i;
//				res = true;
//	         break;
//	      }
//      } else {
//         for (int k = 0; k < Role->GetCount(); k++) {
//				if (res) {
//               break;
//            }
//         	String t1 = Role->GetField(k);
//            String RealValue = Role->GetValue(t1);
//				RealValue = MyLowerCase(RealValue);
//				String Value = MyLowerCase(edSearch->Text);
//				if (RealValue.Pos(Value) != 0) {
//					roleList->ItemIndex = i;
//					res = true;
//	      	   break;
//		      }
//         }
//      }
//   }
//
//	if (roleList->ItemIndex == -1) {
//		Button6->Enabled = false;
//	} else {
//		Button6->Enabled = true;
//	}
//
//	Screen->Cursor = crDefault;
//
//	if (!res) {
//		lsMessageDlg("Ничего не найдено", "Поиск завершен");
//	}
}
//---------------------------------------------------------------------------

void __fastcall lscPresenterRoleListTune::listRoleTypeClick()
{
	m_View->setNumPage(1);
    this->fillRoleList();
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::fillRoleList()
{
	__try
	{
		if (m_View->getCurrentRoleType() < 0)
		{
			return;
		}

		if (Engine == NULL)
		{
			return;
		}

        m_View->StartWaitProccess();

		this->clearRoleList();

		m_Model->currentRoleType = m_View->getCurrentRoleType();
		m_Model->fillRoleList();
        m_View->setPageCount(m_Model->getPageCount());
		for (int i = m_Model->m_offset; i < m_Model->getRoleListCount(); i++)
		{
			if (i >= m_Model->m_limit + m_Model->m_offset)
			{
				break;
			}

			CRole *Role = m_Model->getRoleListItem(i);

			m_View->addItemRoleList();

			for (int j = 0; j < m_Model->getVisiParamsCount(); j++)
			{
				String param = m_Model->getVisiParams(j);
				m_View->addTextToItemRoleList(i - m_Model->m_offset, j, Role->GetValue(param));
			}

	//		m_View->setRoleVisible(i, Role->GetBoolOrTrue("Visible"));
		}
	}
	__finally
	{
		m_View->StopWaitProccess();
	}
}
//---------------------------------------------------------------------------

void lscPresenterRoleListTune::setFilterProperty()
{
	m_Model->setFilterProperty(m_View->getSearchType());
}
//---------------------------------------------------------------------------
void lscPresenterRoleListTune::setFilterText(String text)
{
	m_Model->setFilterText(text);

	m_Model->m_offset = 0;
	m_View->setNumPage(1);
	this->fillRoleList();
}
//---------------------------------------------------------------------------

void __fastcall lscPresenterRoleListTune::clearRoleList()
{
	m_View->clearRoleList();
	m_Model->clearRoleList();
}

//---------------------------------------------------------------------------

void __fastcall lscPresenterRoleListTune::roleListClickCheck()
{
	//
//	if (ListBox1->ItemIndex < 0) {
//      return;
//	}
//	if (roleList->ItemIndex < 0) {
//		return;
//	}
//	CRoleCollect *RoleCollect = Engine->RoleDb->GetRoleCollect(ListBox1->ItemIndex);
//	CRole *Role = (CRole*)roleList->Items->Objects[roleList->ItemIndex];
//	TCheckBoxState State = roleList->State[roleList->ItemIndex];
//
//	CRoleCollectDll *RoleCollectDll = RoleCollect->RoleCollectDll;
//	RoleCollectDll->SetRole(Role);
//	CSetOrder *Difference = new CSetOrder;
//	if (State == cbUnchecked) {
//		Difference->Add("Visible","False");
//	} else {
//		Difference->Add("Visible","True");
//	}
//	RoleCollectDll->AddDifference(Difference);
//	RoleCollectDll->GetRole(Role);
//	Engine->Database->SaveRoleCollectToDb(RoleCollect);
//	delete Difference;
//	ListBox1Click(NULL);
}
//---------------------------------------------------------------------------

void __fastcall lscPresenterRoleListTune::editRole()
{
	int Num = m_View->getCurrentIndexRoleList();
	if (Num == -1) {
		return;
	}
	// Пытаемся изменить роль.
	CRole *role = m_Model->getRoleListItem(Num);

	int ModalResult = mrNone;

	if (Engine->Params->GetValue("RoleScope") == role->Type)
	{
		fmRoleAndInfComTune->setEditRoleData(m_View->getCurrentRoleType(), role);
		ModalResult = fmRoleAndInfComTune->ShowModal();
	} else {

		fmRoleTune->setData(m_View->getCurrentRoleType(), role);
		ModalResult = fmRoleTune->ShowModal();
	}

	CRoleCollect *RoleCollect = Engine->RoleDb->GetRoleCollect(m_View->getCurrentRoleType());
	// По результатам работы окна создаём или не создаём новый экземпляр роли.
	if (ModalResult == mrOk)
	{
    	this->fillRoleList();
	}
}
//---------------------------------------------------------------------------

void __fastcall lscPresenterRoleListTune::addRole()
{
	int Num = m_View->getCurrentRoleType();
	if (Num == -1) {
		return;
	}

	CRoleCollect *RoleCollect = Engine->RoleDb->GetRoleCollect(Num);
	if (RoleCollect == NULL) {
		//todo: Тут должна быть ошибка
		return;
	}
	int ModalResult = mrNone;

	fmRoleTune->setNewRoleData(m_View->getCurrentRoleType(), NULL);

	if (fmRoleTune->ShowModal() == mrOk)
	{
		this->fillRoleList();
	}
}
//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::nextPage()
{
	if (m_Model->getRoleListCount() - (m_Model->m_offset + m_Model->m_limit) > 0)
	{
		m_Model->m_offset += m_Model->m_limit;

		this->fillRoleList();

		int pageNum = m_Model->m_offset/m_Model->m_limit + 1;

		m_View->setNumPage(pageNum);
	}
}
//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::previousPage()
{
	if (m_Model->m_offset != 0)
	{
		m_Model->m_offset -= m_Model->m_limit;

		this->fillRoleList();

		int pageNum = 1;

		pageNum = m_Model->m_offset/m_Model->m_limit + 1;

		m_View->setNumPage(pageNum);
	}
}
//---------------------------------------------------------------------------
void __fastcall lscPresenterRoleListTune::setPage()
{
	int page = m_View->getNumPage() - 1;
	if (page < 0)
	{
		page = 0;
		m_View->setNumPage(page + 1);
	} else if (page > m_Model->getPageCount())
	{
		page = m_Model->getPageCount() - 1;
		m_View->setNumPage(page + 1);
	}

	if (page < 0) return;

	m_Model->m_offset = page * m_Model->m_limit;

	this->fillRoleList();
}
//---------------------------------------------------------------------------
void lscPresenterRoleListTune::visiParamEdit()
{
	if (frmSaveParamRoleList->ShowModal() == mrOk)
	{
		m_Model->loadVisiParams();
		m_View->deleteAllColumn();

		this->fillRoleListColumn();
		this->fillRoleList();
	}
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
