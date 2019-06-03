#ifndef lsiViewMainH
#define lsiViewMainH

struct lsiDBViewerView
{
	virtual int addItemDocCirculList() = 0;
	virtual void addValueToDocCirculList(int row, int column, String text) = 0;
	virtual void addColumn(String columnCaption) = 0;
	virtual void addStatusImage(int itemIndex, int imageIndex) = 0;
	virtual void setColumnSize(int indexColumn, String text) = 0;
	virtual void clearExtendDcList() = 0;
	virtual int getExtDocCirculIndex() = 0;
};

#endif
