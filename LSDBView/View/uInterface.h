//---------------------------------------------------------------------------

#ifndef uInterfaceH
#define uInterfaceH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "CEngine.h"
#include <IdBaseComponent.hpp>
#include <IdComponent.hpp>
#include <IdHTTP.hpp>
#include <IdTCPClient.hpp>
#include <IdTCPConnection.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
#include <ImgList.hpp>
#include <CheckLst.hpp>
#include <Dialogs.hpp>

#include "frameRoleListTune.h"
//---------------------------------------------------------------------------
class TFormInterface : public TForm
{
__published:	// IDE-managed Components
	TCheckBox *CheckBox1;
	TLabel *Label1;
	TEdit *Edit1;
	TEdit *Edit2;
	TLabel *Label2;
	TCheckBox *CheckBox2;
	TEdit *Edit3;
	TLabel *Label3;
	TEdit *Edit4;
	TLabel *Label4;
	TButton *Button2;
	TButton *Button3;
	TTreeView *TreeView1;
	TPanel *Panel1;
	TPageControl *PageControl1;
	TTabSheet *TabSheet4;
	TGroupBox *GroupBox4;
	TOpenDialog *OpenDialog1;
	TTabSheet *RoleListTabSheet;
	void __fastcall CheckBox1Click(TObject *Sender);
	void __fastcall CheckBox2Click(TObject *Sender);
	void __fastcall FormShow(TObject *Sender);
	void __fastcall Button2Click(TObject *Sender);
	void __fastcall Button3Click(TObject *Sender);
	void __fastcall TreeView1Click(TObject *Sender);
	void __fastcall RoleListTabSheetResize(TObject *Sender);




private:	// User declarations
	TfrmRoleListTune *m_frameRoleListTune;
public:		// User declarations
	CSetOrder *Params;
	CEngine *Engine;
	__fastcall TFormInterface(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormInterface *FormInterface;
//---------------------------------------------------------------------------
#endif
