//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscModelDateFilter.h"
//---------------------------------------------------------------------------

lscModelDateFilter::lscModelDateFilter(String caption, TDateTime beginDate)
										: m_caption(caption), m_beginDate(beginDate)
{
}

TDateTime lscModelDateFilter::getBeginDate()
{
	return m_beginDate;
}

TDateTime lscModelDateFilter::getEndDate()
{
	return Now();
}

String lscModelDateFilter::getCaption()
{
	return m_caption;
}

#pragma package(smart_init)
