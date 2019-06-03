//---------------------------------------------------------------------------

#ifndef lscModelPagingH
#define lscModelPagingH
//---------------------------------------------------------------------------
#include <iostream>
#include <vector>

using namespace std;

class lscModelPaging
{
private:
	int m_offset;
	int m_limit;

	int m_pageCount;
	int m_currentPage;

	vector<int> m_vectorLimit;

	void calcOffset();
public:
	lscModelPaging();
	int getLimit();
	int getOffset();

	void setLimit(int );

	void clear();

	bool setCurrentPage(int);
	int getCurrentPage();

//	bool nextPage();
//	bool previousPage();

	int getPageCount();
	void calcPageCount(int);

	vector<int>* getVectorLimit();
};

#endif
