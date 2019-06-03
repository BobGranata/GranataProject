//---------------------------------------------------------------------------

#ifndef lscInitDbThreadH
#define lscInitDbThreadH

#include <Classes.hpp>
//---------------------------------------------------------------------------

typedef void __fastcall (__closure *doSomething)();

class lscInitDbThread : public TThread
{
private:
	doSomething initDb;
	doSomething initView;

	void __fastcall onPostExecute();
protected:
	void __fastcall Execute();
public:
	lscInitDbThread(doSomething _initDb, doSomething _initView, bool CreateSuspended);
};

#endif
