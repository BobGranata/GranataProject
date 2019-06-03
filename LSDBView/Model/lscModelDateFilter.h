//---------------------------------------------------------------------------

#ifndef lscModelDateFilterH
#define lscModelDateFilterH

#include <Classes.hpp>
//---------------------------------------------------------------------------
class lscModelDateFilter
{
	public:
	lscModelDateFilter(String caption, TDateTime beginDate);

	TDateTime getBeginDate();
	TDateTime getEndDate();
	String getCaption();

	private:
	String m_caption;
	TDateTime m_beginDate;
};
#endif
