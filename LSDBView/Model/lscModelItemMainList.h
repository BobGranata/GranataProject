//---------------------------------------------------------------------------

#ifndef lscModelItemMainListH
#define lscModelItemMainListH

#include "lscInteractorExtendDocCircul.h"
//---------------------------------------------------------------------------

class lscModelItemMainList
{
	private:
		lscExtendDocCircul *m_ExtendDocCircul;
	public:
	lscModelItemMainList(lscExtendDocCircul *m_ExtendDocCircul);
	~lscModelItemMainList();
};

#endif
