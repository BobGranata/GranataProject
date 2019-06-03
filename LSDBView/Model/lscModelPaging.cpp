//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscModelPaging.h"
//---------------------------------------------------------------------------

lscModelPaging::lscModelPaging() : m_currentPage(0), m_pageCount(0), m_offset(0), m_limit(1000)
{
	m_vectorLimit.push_back(1);
	m_vectorLimit.push_back(2);
	m_vectorLimit.push_back(4);
    m_vectorLimit.push_back(10);
	m_vectorLimit.push_back(100);
	m_vectorLimit.push_back(200);
	m_vectorLimit.push_back(500);
	m_vectorLimit.push_back(1000);
}
//---------------------------------------------------------------------------
int lscModelPaging::getLimit()
{
    return m_limit;
}
//---------------------------------------------------------------------------
int lscModelPaging::getOffset()
{
    return m_offset;
}
//---------------------------------------------------------------------------
void lscModelPaging::setLimit(int limitIndex)
{
    this->m_limit = m_vectorLimit[limitIndex];
}
//---------------------------------------------------------------------------
void lscModelPaging::clear()
{
	m_currentPage = 0;
	m_pageCount = 0;

    this->calcOffset();
}
//---------------------------------------------------------------------------
bool lscModelPaging::setCurrentPage(int currentPage)
{
	if (m_currentPage == currentPage)
		return false;

	if (currentPage < 0)
	{
//		m_currentPage = 0;
		return false;
	}
	else if (currentPage >= m_pageCount)
	{
		return false;
	}
	else
	{
		m_currentPage = currentPage;
	}

	this->calcOffset();

	return true;
}
//---------------------------------------------------------------------------
int lscModelPaging::getCurrentPage()
{
    return m_currentPage;
}
//---------------------------------------------------------------------------
//bool lscModelPaging::nextPage()
//{
//	if (m_currentPage + 1 >= m_pageCount)
//	{
//		return false;
//	}
//
//	m_currentPage++;
//    this->calcOffset();
//
//	return true;
//}
////---------------------------------------------------------------------------
//bool lscModelPaging::previousPage()
//{
//	if (m_currentPage - 1 < 0)
//	{
//		return false;
//	}
//
//	m_currentPage--;
//    this->calcOffset();
//
//	return true;
//}
//---------------------------------------------------------------------------
void lscModelPaging::calcOffset()
{
	this->m_offset = m_currentPage * this->m_limit;
}
//---------------------------------------------------------------------------
int lscModelPaging::getPageCount()
{
	return m_pageCount;
}
//---------------------------------------------------------------------------
void lscModelPaging::calcPageCount(int countItem)
{
	if (countItem != 0)
	{
		int test = countItem/this->m_limit + 1;
		this->m_pageCount = test;
	}
}
//---------------------------------------------------------------------------
vector<int>* lscModelPaging::getVectorLimit()
{
    return &m_vectorLimit;
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
