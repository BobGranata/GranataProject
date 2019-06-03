#ifndef lsiViewRoleListTuneH
#define lsiViewRoleListTuneH

struct lsiViewRoleListTune {
	virtual void addFindParams(String param, int index) = 0;

	virtual void addColumn(String param, int index) = 0;
	virtual void deleteAllColumn() = 0;
	virtual void addTextToItemRoleList(int row, int column, String text) = 0;

	virtual void clearRoleTypeList() = 0;
	virtual void addItemRoleTypeList(String roleType, int roleTypeIndex) = 0;
	virtual int getCurrentRoleType() = 0;
	virtual void setCurrentRoleType(int index) = 0;

	virtual int getSearchType() = 0;
	virtual void setSearchType(int index) = 0;

	virtual void setEditRoleEnable(bool enable) = 0;

	virtual int getCurrentIndexRoleList() = 0;
	virtual void setCurrentIndexRoleList(int index) = 0;

	virtual int addItemRoleList() = 0;
	virtual void clearRoleList() = 0;

	virtual void setRoleVisible(int roleIndex, bool isVisible) = 0;

	virtual String getSearchText() = 0;

	virtual void setPageCount(int count) = 0;
	virtual int getNumPage() = 0;
	virtual void setNumPage(int page) = 0;

	virtual void StartWaitProccess() = 0;
	virtual void StopWaitProccess() = 0;
};

#endif
