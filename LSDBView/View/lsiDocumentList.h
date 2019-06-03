#ifndef lsiDocumentListH
#define lsiDocumentListH

#include "lscInteractorExtendDocCircul.h"

struct lsiDocumentList
{
	virtual void fillDocumentList(lscExtendDocCircul *extendDocCircul) = 0;
};

#endif
