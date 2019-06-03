//---------------------------------------------------------------------------

#ifndef lscPresenterDocListH
#define lscPresenterDocListH

#include "lsiListColumnView.h"
#include "lscInteractorExtendDocCircul.h"
#include "CSingleEngine.h"
//---------------------------------------------------------------------------

class lscPresenterDocList
{
private:
	lsiListColumnView *m_View;

	lscExtendDocCircul *m_extendDocCircul;

	CInfComDll *getPrepareInfComDll();

	CInfComDll *m_InfComDll;

public:
	lscPresenterDocList(lsiListColumnView *view);

	void viewDocument();
	void resendPack();
	void fillDocumentList(lscExtendDocCircul *extendDocCircul);

};


#endif
