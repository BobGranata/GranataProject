#include "cDbQuery.h"
#include "cRequirementsModel.h"
#include <fstream>

cDbQuery::cDbQuery(iDbLoader *dbLoader) : m_dbLoader(dbLoader)
{

}

cDbQuery::~cDbQuery()
{

}

void cDbQuery::getReportByParam(string reportFileName, string Name, int param)
{
    vector<cFirmModel> *vectorFirms = m_dbLoader->vectorFirms();

    vector<cRequirementsModel> resultVector;

    ofstream fileReport(reportFileName);
    if (!fileReport) return;

    for (auto it = vectorFirms->cbegin(); it != vectorFirms->cend(); it++)
    {
        vector<cRequirementsModel*> *vectorReq = it->vRequirements();
        for (auto itReq = vectorReq->cbegin(); itReq != vectorReq->cend(); itReq++)
        {
            if (param == 0) {
                if (Name != (*itReq)->fruitProducer())
                {
                    continue;
                }
            } else if (param == 1) {
                if (Name != (*itReq)->fruitName())
                {
                    continue;
                }
            }

            fileReport << it->firmName();
            fileReport << " ";
            fileReport << (*itReq)->fruitName();
            fileReport << " ";
            fileReport << (*itReq)->fruitMaxPrice();
            fileReport << " ";
            fileReport << (*itReq)->fruitProducer();
            fileReport << " ";
            fileReport << (*itReq)->fruitAmount();
            fileReport << endl;
        }
    }
    fileReport.close();
}
