//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscInitDbThread.h"
//---------------------------------------------------------------------------

lscInitDbThread::lscInitDbThread(doSomething _initDb, doSomething _initView, bool CreateSuspended) : TThread(CreateSuspended)
{
	initDb = _initDb;
	initView = _initView;
}

void __fastcall lscInitDbThread::Execute()
{
//---- Place thread code here ----
	initDb();

	Synchronize(onPostExecute);
}

void __fastcall lscInitDbThread::onPostExecute()
{
	initView();
}

#pragma package(smart_init)
