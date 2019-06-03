//---------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop

#include <memory>
#include "uSaveParamRoleList.h"
//---------------------------------------------------------------------
#pragma resource "*.dfm"
TfrmSaveParamRoleList *frmSaveParamRoleList;

const String paramListPath = "Lists\\VisiRoleListParams.txt";
//--------------------------------------------------------------------- 
__fastcall TfrmSaveParamRoleList::TfrmSaveParamRoleList(TComponent* AOwner)
	: TForm(AOwner)
{
}
//---------------------------------------------------------------------
void __fastcall TfrmSaveParamRoleList::OKBtnClick(TObject *Sender)
{
	memoParamList->Lines->SaveToFile(ExtractFilePath(Application->ExeName) + paramListPath);
	this->ModalResult = mrOk;
}
//---------------------------------------------------------------------------

void __fastcall TfrmSaveParamRoleList::CancelBtnClick(TObject *Sender)
{
	//
}
//---------------------------------------------------------------------------

void __fastcall TfrmSaveParamRoleList::FormShow(TObject *Sender)
{
	std::unique_ptr<TStringList> visiParams(new TStringList());
	String path = ExtractFilePath(Application->ExeName) + paramListPath;
	if (!FileExists(path)) {
		visiParams->Add("Name");
		visiParams->Add("INN");
		visiParams->SaveToFile(path);
	} else {
		visiParams->LoadFromFile(path);
	}

	memoParamList->Lines->LoadFromFile(path);
}
//---------------------------------------------------------------------------

