//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscFilterModel.h"
#include "CSingleEngine.h"
//---------------------------------------------------------------------------

lscFilterModel::lscFilterModel() : m_Filter()
{
}

//---------------------------------------------------------------------------
lscFilterModel::~lscFilterModel()
{
}

//---------------------------------------------------------------------------
void lscFilterModel::init()
{
	m_DcTypeFilterList.clear();
	m_dateDcFilterList.clear();
	m_rolePrmFilterList.clear();
	m_dcPrmFilterList.clear();
	m_infcomPrmFilterList.clear();
	m_documentStatusFilterList.clear();
	m_documentPrmFilterList.clear();

	m_Filter.RoleType = "Enterprise";

	CEngine *dbEngine = CSingleEngine::Get();

	for (int i = 0; i < dbEngine->InfComDb->GetCount(); i++)
	{
//		m_dbEngine->InfComDb->GetInfComCollectByType();

		CInfComCollect * InfComCollect = dbEngine->InfComDb->GetInfComCollect(i);
		CInfComDll *InfComDll = InfComCollect->InfComDll;

		String sCaptionInfComType = InfComDll->GetLocalInfComType();
		// Создаём закладки для различных ИВ.
		for (int j = 0; j < InfComDll->GetDocCirculTypeCount(); j++)
		{
			String sDocCirculLocalName = InfComDll->GetLocalDocCirculTypeName(j);
			String sDocCirculName = InfComDll->GetDocCirculTypeName(j);
			m_DcTypeFilterList.push_back(pair<String, String>(sDocCirculLocalName, sDocCirculName));
		}
	}
	m_DcTypeFilterList.push_back(pair<String, String>("Все подряд", ""));

	m_DcStatusFilterList.push_back(pair<String, String>("Завершённые", "CorrectEnd"));
	m_DcStatusFilterList.push_back(pair<String, String>("Ошибочные", "ErrorEnd"));
	m_DcStatusFilterList.push_back(pair<String, String>("Не завершённые", "Processed"));
	m_DcStatusFilterList.push_back(pair<String, String>("Все подряд", ""));


	m_documentStatusFilterList.push_back("Exported");
	m_documentStatusFilterList.push_back("Ready");
	m_documentStatusFilterList.push_back("CorrectEndOut");
	m_documentStatusFilterList.push_back("CorrectEndIn");
	m_documentStatusFilterList.push_back("ErrorEndIn");
	m_documentStatusFilterList.push_back("ErrorEndOut");
	m_documentStatusFilterList.push_back("Arrived");
	m_documentStatusFilterList.push_back("Unknown");
	m_documentStatusFilterList.push_back(""); //Все подряд

	m_documentPrmFilterList.push_back("Message-ID");
	m_documentPrmFilterList.push_back("MainDocumentId");
	m_documentPrmFilterList.push_back("FileName");

	m_dateDcFilterList.push_back(lscModelDateFilter("весь период", NULL));
	m_dateDcFilterList.push_back(lscModelDateFilter("сегодня", Date()));
	m_dateDcFilterList.push_back(lscModelDateFilter("месяц", IncMonth(Date(), -1)));
	m_dateDcFilterList.push_back(lscModelDateFilter("3 месяца", IncMonth(Date(), -3)));
	m_dateDcFilterList.push_back(lscModelDateFilter("пол года", IncMonth(Date(), -6)));
	m_dateDcFilterList.push_back(lscModelDateFilter("год", IncMonth(Date(), -12)));

	m_rolePrmFilterList.push_back("INN");
	m_rolePrmFilterList.push_back("Name");
	m_rolePrmFilterList.push_back("PFR_Reg");
	m_rolePrmFilterList.push_back("InvoiceCode");

	m_dcPrmFilterList.push_back("DocCirculID");
	m_dcPrmFilterList.push_back("ID");
	m_dcPrmFilterList.push_back("FileName");

	m_infcomPrmFilterList.push_back("Name");
}

// параметры роли
//---------------------------------------------------------------------------
void lscFilterModel::setRoleFilterParam(int index, String text)
{
	m_Filter.clearRoleParams();
	if (index > m_rolePrmFilterList.size())
		return;
	String param = m_rolePrmFilterList[index];
	m_Filter.addRoleParams(param, text);
}

//---------------------------------------------------------------------------
int lscFilterModel::getRolePrmFilterCount() const
{
	return m_rolePrmFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getRolePrmFilterItem(int index) const
{
	if (index < m_rolePrmFilterList.size())
		return m_rolePrmFilterList[index];
	else
		return "";
}

// параметры даты ДО
//---------------------------------------------------------------------------
int lscFilterModel::getDatePrmFilterCount() const
{
	return m_dateDcFilterList.size();
}

//---------------------------------------------------------------------------
lscModelDateFilter lscFilterModel::getDatePrmFilterItem(int index) const
{
	return m_dateDcFilterList[index];
}

//---------------------------------------------------------------------------
void lscFilterModel::setDateFilterPrm(TDateTime beginDate, TDateTime endDate)
{
	m_Filter.DateBegin = beginDate;
	m_Filter.DateEnd = endDate;
}

// параметры выбора типа ДО
//---------------------------------------------------------------------------
int lscFilterModel::getDcTypeFilterCount() const
{
	return m_DcTypeFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getDcTypeFilterItem(int index) const
{
	return m_DcTypeFilterList[index].first;
}

//---------------------------------------------------------------------------
void lscFilterModel::setDcTypeFilterParam(int index)
{
	String dcType = "";
	if (index != -1)
		dcType = m_DcTypeFilterList[index].second;

	m_Filter.DocCirculType = dcType;
}

//---------------------------------------------------------------------------
// параметры статуса ДО
int lscFilterModel::getDcStatusFilterCount() const
{
	return m_DcStatusFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getDcStatusFilterItem(int index) const
{
	return m_DcStatusFilterList[index].first;
}

//---------------------------------------------------------------------------
void lscFilterModel::setDcStatusFilterParam(int index)
{
	String dcStatus = "";
	if (index != -1)
		dcStatus = m_DcStatusFilterList[index].second;

	m_Filter.SetStatus(dcStatus);
}

//---------------------------------------------------------------------------
const vector<pair<String, String> > &lscFilterModel::getDcStatusParam()
{
	return m_DcStatusFilterList;
}


//---------------------------------------------------------------------------
// параметры ДО
int lscFilterModel::getDcPrmFilterCount() const
{
	return m_dcPrmFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getDcPrmFilterItem(int index) const
{
	return m_dcPrmFilterList[index];
}

//---------------------------------------------------------------------------
void lscFilterModel::setDcFilterParam(int index, String text)
{
	m_Filter.clearDcParams();
	if (index > m_dcPrmFilterList.size())
		return;
	String param = m_dcPrmFilterList[index];
	m_Filter.addDcParams(param, text);
}

//---------------------------------------------------------------------------
// параметры направления
int lscFilterModel::getInfComPrmFilterCount() const
{
	return m_infcomPrmFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getInfComPrmFilterItem(int index) const
{
	return m_infcomPrmFilterList[index];
}

//---------------------------------------------------------------------------
void lscFilterModel::setInfComFilterParam(int index, String text)
{
	m_Filter.clearInfComParams();
	if (index > m_infcomPrmFilterList.size())
		return;
	String param = m_infcomPrmFilterList[index];
	m_Filter.addInfComParams(param, text);
}

//---------------------------------------------------------------------------
// параметры документа
int lscFilterModel::getDocumentPrmFilterCount() const
{
	return m_documentPrmFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getDocumentPrmFilterItem(int index) const
{
	return m_documentPrmFilterList[index];
}

//---------------------------------------------------------------------------
void lscFilterModel::setDocumentFilterParam(int index, String text)
{
	m_Filter.clearDocumentParams();
	if (index > m_documentPrmFilterList.size())
		return;
	String param = m_documentPrmFilterList[index];
	m_Filter.addDocumentParams(param, text);
}

//---------------------------------------------------------------------------
// статус документа
int lscFilterModel::getDocumentStatusFilterCount() const
{
	return m_documentStatusFilterList.size();
}

//---------------------------------------------------------------------------
String lscFilterModel::getDocumentStatusFilterItem(int index) const
{
	return m_documentStatusFilterList[index];
}

//---------------------------------------------------------------------------
void lscFilterModel::setDocumentStatusFilterParam(int index)
{
	String documentStatus = "";
	if (index != -1)
		documentStatus = m_documentStatusFilterList[index];

	m_Filter.SetDocumentStatus(documentStatus);
}


//---------------------------------------------------------------------------
#pragma package(smart_init)
