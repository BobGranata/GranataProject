//----------------------------------------------------------------------------
#ifndef uSaveParamRoleListH
#define uSaveParamRoleListH
//----------------------------------------------------------------------------
#include <Vcl.ExtCtrls.hpp>
#include <Vcl.Buttons.hpp>
#include <Vcl.StdCtrls.hpp>
#include <Vcl.Controls.hpp>
#include <Vcl.Forms.hpp>
#include <Vcl.Graphics.hpp>
#include <System.Classes.hpp>
#include <System.SysUtils.hpp>
#include <Winapi.Windows.hpp>
#include <System.hpp>
//----------------------------------------------------------------------------
class TfrmSaveParamRoleList : public TForm
{
__published:
	TButton *OKBtn;
	TButton *CancelBtn;
	TMemo *memoParamList;
	void __fastcall OKBtnClick(TObject *Sender);
	void __fastcall CancelBtnClick(TObject *Sender);
	void __fastcall FormShow(TObject *Sender);
private:
public:
	virtual __fastcall TfrmSaveParamRoleList(TComponent* AOwner);
};
//----------------------------------------------------------------------------
extern PACKAGE TfrmSaveParamRoleList *frmSaveParamRoleList;
//----------------------------------------------------------------------------
#endif    
