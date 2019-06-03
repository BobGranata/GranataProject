#ifndef IDBQUERY_H
#define IDBQUERY_H

#include <iostream>

using namespace std;

struct iDbQuery
{
    virtual ~iDbQuery() {}
    virtual void getReportByParam(string reportFileName, string name, int param) = 0;
};

#endif // IDBQUERY_H
