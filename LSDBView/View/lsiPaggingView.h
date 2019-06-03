#ifndef lsiPaggingViewH
#define lsiPaggingViewH

struct lsiPaggingView
{
	virtual int getPage() = 0;
	virtual void setPage(int) = 0;
	virtual void setPageCount(int page) = 0;
	virtual int getPaggingLimitIndex() = 0;
	virtual void setPaggingLimit(int) = 0;
	virtual void addPaggingLimit(int limit) = 0;
};

#endif
