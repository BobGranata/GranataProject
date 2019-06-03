#ifndef lsiLoadingViewH
#define lsiLoadingViewH

struct lsiLoadingView
{
   virtual void showLoading() = 0;
   virtual void hideLoading() = 0;
};

#endif