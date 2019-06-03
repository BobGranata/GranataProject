//---------------------------------------------------------------------------


#pragma hdrstop

#include <FileCtrl.hpp>
#include "uInterface.h"
#include "Code.h"
#include "Character.hpp"
#include "URoleAndInfComTune.h"
#include "URoleTune.h"
#include "CXmlParams.h"
//#include "UMessageDlg.h"

#include "paramsProxy.h"
//---------------------------------------------------------------------------
//#pragma package(smart_init)
#pragma resource "*.dfm"
TFormInterface *FormInterface;
//---------------------------------------------------------------------------
__fastcall TFormInterface::TFormInterface(TComponent* Owner)
	: TForm(Owner)
{
	Params = NULL;
	Engine = NULL;

	// Фрейм создаётся но не удаляется. Это нормально при условии что форма настроек
	// создаётся один раз и существует весь жизненный цикл приложения
	m_frameRoleListTune = new TfrmRoleListTune(RoleListTabSheet);
	m_frameRoleListTune->Parent = RoleListTabSheet;
	m_frameRoleListTune->Height = RoleListTabSheet->Height;
	m_frameRoleListTune->Width = RoleListTabSheet->Width;
}
//---------------------------------------------------------------------------
void __fastcall TFormInterface::CheckBox1Click(TObject *Sender)
{
	Label1->Enabled = CheckBox1->Checked;
	Edit1->Enabled = CheckBox1->Checked;
	Label2->Enabled = CheckBox1->Checked;
	Edit2->Enabled = CheckBox1->Checked;
	CheckBox2->Enabled = CheckBox1->Checked;
	CheckBox2Click(NULL);
}
//---------------------------------------------------------------------------
void __fastcall TFormInterface::CheckBox2Click(TObject *Sender)
{
	Label3->Enabled = CheckBox1->Checked && CheckBox2->Checked;
	Edit3->Enabled = CheckBox1->Checked && CheckBox2->Checked;
	Label4->Enabled = CheckBox1->Checked && CheckBox2->Checked;
	Edit4->Enabled = CheckBox1->Checked && CheckBox2->Checked;
}
//---------------------------------------------------------------------------
void __fastcall TFormInterface::FormShow(TObject *Sender)
{
	Height = Screen->Height - 100;

	m_frameRoleListTune->init(Engine, Params);

	if (Params != NULL) {
		// Настройка внешнего вида окна.
		TreeView1->Select(TreeView1->Items->Item[0]);
		PageControl1->ActivePageIndex = 0;

		// Настройки обновления.
		CheckBox1->Checked = Proxy::getEnabled(Params);
		Edit1->Text = Proxy::getServer(Params);
		Edit2->Text = Proxy::getPortStr(Params);
		CheckBox2->Checked = Proxy::getAuth(Params);
		Edit3->Text = Proxy::getUsername(Params);
		Edit4->Text = Proxy::getPassword(Params);

	}
	CheckBox1Click(NULL);
}
//---------------------------------------------------------------------------
void __fastcall TFormInterface::Button2Click(TObject *Sender)
{
	ModalResult = mrOk;
}
//---------------------------------------------------------------------------

void __fastcall TFormInterface::Button3Click(TObject *Sender)
{
	ModalResult = mrCancel;
}
//---------------------------------------------------------------------------

void __fastcall TFormInterface::TreeView1Click(TObject *Sender)
{
	PageControl1->ActivePageIndex = TreeView1->Selected->Index;
}
//---------------------------------------------------------------------------

void __fastcall TFormInterface::RoleListTabSheetResize(TObject *Sender)
{
//	m_frameRoleListTune->;
}
//---------------------------------------------------------------------------

