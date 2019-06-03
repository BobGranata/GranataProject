#ifndef lsiFilterViewH
#define lsiFilterViewH

struct lsiFilterView
{
	virtual void clearFilter() = 0;

	virtual String getRoleFilterText() = 0;
	virtual int getRoleFilterParam() = 0;
	virtual void addRoleFilterParam(String param) = 0;

	virtual String getDcFilterText() = 0;
	virtual int getDcFilterParam() = 0;
	virtual void addDcFilterParam(String param) = 0;

	virtual String getInfComFilterText() = 0;
	virtual int getInfComFilterParam() = 0;
	virtual void addInfComFilterParam(String param) = 0;

	virtual int getDateFilterParam() = 0;
	virtual void setBeginDate(TDateTime) = 0;
	virtual void setEndDate(TDateTime) = 0;
	virtual TDateTime getBeginDate() = 0;
	virtual TDateTime getEndDate() = 0;
	virtual void addDateFilterParam(String param) = 0;

	virtual void addDcTypeFilterParam(String param) = 0;
	virtual int getDcTypeFilterParam() = 0;

	virtual void addDcStatusFilterParam(String param) = 0;
	virtual int getDcStatusFilterParam() = 0;

	virtual void addDocumentStatusFilterParam(String param) = 0;
	virtual int getDocumentStatusFilterParam() = 0;

	virtual String getDocumentFilterText() = 0;
	virtual int getDocumentFilterParam() = 0;
	virtual void addDocumentFilterParam(String param) = 0;
};

#endif
