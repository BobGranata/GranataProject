#ifndef CDBQUERY_H
#define CDBQUERY_H

#include "iDbLoader.h"
#include "iDbQuery.h"

class cDbQuery : public iDbQuery
{
private:
    iDbLoader *m_dbLoader;
public:
    cDbQuery(iDbLoader *dbLoader);
    ~cDbQuery();

    void getReportByParam(string reportFileName, string producerName, int param);
};

#endif // CDBQUERY_H
