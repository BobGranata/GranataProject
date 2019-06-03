//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
#include <tchar.h>

#include "UMainForm.h"

//---------------------------------------------------------------------------
#include <Vcl.Styles.hpp>
#include <Vcl.Themes.hpp>
USEFORM("..\..\Modules\Interface\ResendLetter\uResendLetter.cpp", fResendLetter);
USEFORM("..\..\Modules\Interface\ProgramTuneProvider\UView.cpp", fmView);
USEFORM("..\..\Modules\Interface\ProgramTuneProvider\URoleAndInfComTune.cpp", fmRoleAndInfComTune);
USEFORM("..\..\Modules\Interface\ProgramTuneProvider\URoleTune.cpp", fmRoleTune);
USEFORM("..\..\Modules\Interface\StartProgress\uLoadingWait.cpp", frmLoadingWait);
USEFORM("..\..\Modules\Interface\Style\UDocCirculImage.cpp", DataModuleDcImage); /* TDataModule: File Type */
USEFORM("..\..\Modules\Interface\Style\uViewerData.cpp", dmViewer); /* TDataModule: File Type */
USEFORM("UMainForm.cpp", frmMain);
USEFORM("View\frameRoleListTune.cpp", frmRoleListTune); /* TFrame: File Type */
USEFORM("View\uInterface.cpp", FormInterface);
USEFORM("View\uSaveParamRoleList.cpp", frmSaveParamRoleList);
USEFORM("View\uDocumentList.cpp", frmDocumentList);
USEFORM("..\..\Modules\Interface\ChoiceBase\UChoiceBase.cpp", fmChoiceBase);
USEFORM("..\..\Modules\Interface\ProgramTuneProvider\UInfComTune.cpp", fmInfComTune);
USEFORM("..\..\Modules\Interface\ChoiceBase\UAddDataBase.cpp", addEditForm);
//---------------------------------------------------------------------------
int WINAPI _tWinMain(HINSTANCE, HINSTANCE, LPTSTR, int)
{
	try
	{
		Application->Initialize();
		Application->MainFormOnTaskBar = true;

		Application->CreateForm(__classid(TfrmMain), &frmMain);
		Application->CreateForm(__classid(TformInfo), &formInfo);
		Application->CreateForm(__classid(TFormReportEnterpriseSummary), &FormReportEnterpriseSummary);
		Application->CreateForm(__classid(TformInfo), &formInfo);
		Application->CreateForm(__classid(TFormReportCertList), &FormReportCertList);
		Application->CreateForm(__classid(TFormReportCertListPFR), &FormReportCertListPFR);
		Application->CreateForm(__classid(TFormReportEnterpriseSummary), &FormReportEnterpriseSummary);
		Application->CreateForm(__classid(TFormReportEnterpriseSummaryPfr), &FormReportEnterpriseSummaryPfr);
		Application->CreateForm(__classid(TForm3), &Form3);
		Application->CreateForm(__classid(TfmInfComTune), &fmInfComTune);
		Application->CreateForm(__classid(TfmRoleAndInfComTune), &fmRoleAndInfComTune);
		Application->CreateForm(__classid(TfmRoleTune), &fmRoleTune);
		Application->CreateForm(__classid(TFormInterface), &FormInterface);
		Application->CreateForm(__classid(TfrmSaveParamRoleList), &frmSaveParamRoleList);
		Application->CreateForm(__classid(TdmViewer), &dmViewer);
		Application->CreateForm(__classid(TfmView), &fmView);
		Application->CreateForm(__classid(TDataModuleDcImage), &DataModuleDcImage);
		Application->CreateForm(__classid(TfrmLoadingWait), &frmLoadingWait);
		Application->Run();
	}
	catch (Exception &exception)
	{
		Application->ShowException(&exception);
	}
	catch (...)
	{
		try
		{
			throw Exception("");
		}
		catch (Exception &exception)
		{
			Application->ShowException(&exception);
		}
	}
	return 0;
}
//---------------------------------------------------------------------------
