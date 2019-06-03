//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscPresenterMain.h"
#include "UChoiceBase.h"
//---------------------------------------------------------------------------

lscPresenterMain::lscPresenterMain(lsiDBViewerView *view, lsiDocumentList *documentList, lsiFilterView *filterView, lsiPaggingView *paggingView, lsiLoadingView *loadingView)
						: m_View(view), m_documentList(documentList), m_filterView(filterView), m_paggingView(paggingView), m_loadingView(loadingView)
{
	m_Model = new lscModelMain();
	m_FilterModel = new lscFilterModel();
}
//---------------------------------------------------------------------------
lscPresenterMain::~lscPresenterMain()
{
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::init()
{
	String pathToDb = this->setBase();
	if (pathToDb.IsEmpty())
	{
		Application->Terminate();
	}

//	m_loadingView->showLoading();
//	m_loadingView->hideLoading();

//	m_InitThread = new lscInitDbThread(initDb, initView, false);


	m_Model->init(pathToDb);

	this->initFilter();
	this->initPagging();

	this->fillRoleListColumn();
}

void __fastcall lscPresenterMain::initDb()
{
//	m_Model->init("E:\\Тесты\\тестовые базы\\DBOperator_R_RATE_short\\");
}

void __fastcall lscPresenterMain::initView()
{
	this->initFilter();
	this->initPagging();

	this->fillRoleListColumn();

	m_loadingView->hideLoading();
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::updateDB()
{
	m_Model->update();

	this->findDocCirculClick();
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::initPagging()
{
	lscModelPaging *paging = m_Model->getPaging();
	vector<int> *vectorLimit = paging->getVectorLimit();
	for (vector<int>::iterator it = vectorLimit->begin(); it != vectorLimit->end(); it++)
	{
		m_paggingView->addPaggingLimit(*it);
	}
	m_paggingView->setPaggingLimit(vectorLimit->size() - 1);
	m_paggingView->setPageCount(paging->getPageCount());
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::initFilter()
{
	m_FilterModel->init();

    m_filterView->clearFilter();

	for (int i = 0; i < m_FilterModel->getRolePrmFilterCount(); i++)
	{
		m_filterView->addRoleFilterParam(m_FilterModel->getRolePrmFilterItem(i));
	}

	for (int i = 0; i < m_FilterModel->getDatePrmFilterCount(); i++)
	{
		lscModelDateFilter modelDateFilter = m_FilterModel->getDatePrmFilterItem(i);
		m_filterView->addDateFilterParam(modelDateFilter.getCaption());

		if (i == 0)
		{
			m_filterView->setBeginDate(modelDateFilter.getBeginDate());
			m_filterView->setEndDate(modelDateFilter.getEndDate());
		}
	}

	for (int i = 0; i < m_FilterModel->getDcTypeFilterCount(); i++)
	{
		m_filterView->addDcTypeFilterParam(m_FilterModel->getDcTypeFilterItem(i));
	}

	for (int i = 0; i < m_FilterModel->getDcPrmFilterCount(); i++)
	{
		m_filterView->addDcFilterParam(m_FilterModel->getDcPrmFilterItem(i));
	}

	for (int i = 0; i < m_FilterModel->getInfComPrmFilterCount(); i++)
	{
		m_filterView->addInfComFilterParam(m_FilterModel->getInfComPrmFilterItem(i));
	}

	for (int i = 0; i < m_FilterModel->getDcStatusFilterCount(); i++)
	{
		m_filterView->addDcStatusFilterParam(m_FilterModel->getDcStatusFilterItem(i));
	}

	for (int i = 0; i < m_FilterModel->getDocumentPrmFilterCount(); i++)
	{
		m_filterView->addDocumentFilterParam(m_FilterModel->getDocumentPrmFilterItem(i));
	}

	for (int i = 0; i < m_FilterModel->getDocumentStatusFilterCount(); i++)
	{
		m_filterView->addDocumentStatusFilterParam(m_FilterModel->getDocumentStatusFilterItem(i));
	}
}
//---------------------------------------------------------------------------

void __fastcall lscPresenterMain::setAllFilterParams()
{
	m_FilterModel->setRoleFilterParam(m_filterView->getRoleFilterParam(), m_filterView->getRoleFilterText());

	m_FilterModel->setDateFilterPrm(m_filterView->getBeginDate(), m_filterView->getEndDate());

	m_FilterModel->setDcTypeFilterParam(m_filterView->getDcTypeFilterParam());

	m_FilterModel->setDcFilterParam(m_filterView->getDcFilterParam(), m_filterView->getDcFilterText());

	m_FilterModel->setDcStatusFilterParam(m_filterView->getDcStatusFilterParam());

	m_FilterModel->setDocumentFilterParam(m_filterView->getDocumentFilterParam(), m_filterView->getDocumentFilterText());

	m_FilterModel->setDocumentStatusFilterParam(m_filterView->getDocumentStatusFilterParam());

	m_FilterModel->setInfComFilterParam(m_filterView->getInfComFilterParam(), m_filterView->getInfComFilterText());

}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::datePeriodFilterChange()
{
	lscModelDateFilter modelDateFilter = m_FilterModel->getDatePrmFilterItem(m_filterView->getDateFilterParam());

	m_filterView->setBeginDate(modelDateFilter.getBeginDate());
	m_filterView->setEndDate(modelDateFilter.getEndDate());
}

//---------------------------------------------------------------------------
String __fastcall lscPresenterMain::setBase()
{
	TfmChoiceBase *fmChoiceBase = new TfmChoiceBase(NULL);
	String pathToDb = L"";
	if (fmChoiceBase->ShowModal() == mrOk)
	{
		pathToDb = fmChoiceBase->getBasePath();
	}

	delete fmChoiceBase;

	return pathToDb;
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::fillRoleListColumn()
{
	for (int i = 0; i < m_Model->getDocCirculListColumnCount(); i++)
	{
		lscColumnMainList *value = m_Model->getDocCirculColumnParams(i);
		m_View->addColumn(value->getColumnParam());
	}
}

//---------------------------------------------------------------------------
void lscPresenterMain::loadDocumentList()
{
	int index = m_View->getExtDocCirculIndex();

	if (index == -1)
	{
		return;
	}

    lscModelPaging *paging = m_Model->getPaging();
	int offset = paging->getOffset();

	lscExtendDocCircul *extendDocCircul = m_Model->getDocCirculListItem(index + offset);

	m_documentList->fillDocumentList(extendDocCircul);
}

//---------------------------------------------------------------------------
void lscPresenterMain::changePage()
{
	int page = m_paggingView->getPage();

	lscModelPaging *paging = m_Model->getPaging();

	if (paging->setCurrentPage(page))
	{
		m_paggingView->setPage(paging->getCurrentPage());
		findDocCirculClick();
	}
}

//---------------------------------------------------------------------------
void lscPresenterMain::nextPage()
{
	lscModelPaging *paging = m_Model->getPaging();

	if (paging->setCurrentPage(paging->getCurrentPage() + 1))
	{
		this->fillExtendDcList();
		m_paggingView->setPage(paging->getCurrentPage() + 1);
	}
}

//---------------------------------------------------------------------------
void lscPresenterMain::previousPage()
{
	lscModelPaging *paging = m_Model->getPaging();

	if (paging->setCurrentPage(paging->getCurrentPage()-1))
	{
		this->fillExtendDcList();
		m_paggingView->setPage(paging->getCurrentPage() + 1);
	}
}
//---------------------------------------------------------------------------
void lscPresenterMain::firstPage()
{
	lscModelPaging *paging = m_Model->getPaging();
	if (paging->setCurrentPage(0))
	{
		this->fillExtendDcList();
		m_paggingView->setPage(paging->getCurrentPage() + 1);
	}
}
//---------------------------------------------------------------------------
void lscPresenterMain::lastPage()
{
	lscModelPaging *paging = m_Model->getPaging();
	if (paging->setCurrentPage(paging->getPageCount()-1))
	{
		this->fillExtendDcList();
		m_paggingView->setPage(paging->getCurrentPage() + 1);
	}
}
//---------------------------------------------------------------------------
void lscPresenterMain::changeLimit()
{
	lscModelPaging *paging = m_Model->getPaging();
	paging->clear();

	paging->setLimit(m_paggingView->getPaggingLimitIndex());
	paging->calcPageCount(m_Model->getDocCirculCount());

	// в отдельный метод:
	this->setAllFilterParams();

	this->fillExtendDcList();

	m_paggingView->setPage(paging->getCurrentPage() + 1);

	m_paggingView->setPageCount(paging->getPageCount());
}
//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::findDocCirculClick()
{
	lscModelPaging *paging = m_Model->getPaging();
	paging->clear();

	// в отдельный метод:
	this->setAllFilterParams();

	m_loadingView->showLoading();

	m_Model->findDocCircul(&m_FilterModel->m_Filter);

	m_loadingView->hideLoading();

	m_paggingView->setPage(paging->getCurrentPage() + 1);
	m_paggingView->setPageCount(paging->getPageCount());

	this->fillExtendDcList();
}

//---------------------------------------------------------------------------
void __fastcall lscPresenterMain::fillExtendDcList()
{
	m_View->clearExtendDcList();

    lscModelPaging *paging = m_Model->getPaging();

	int offset = paging->getOffset();
	int limit = paging->getLimit();

	for (int i = offset; i < m_Model->getDocCirculCount(); i++)
	{
		if (i >= limit + offset)
		{
			break;
		}

		lscExtendDocCircul *extendDocCircul = m_Model->getDocCirculListItem(i);

		m_View->addItemDocCirculList();

		for (int j = 0; j < m_Model->getDocCirculListColumnCount(); j++)
		{
			lscColumnMainList *param = m_Model->getDocCirculColumnParams(j);

			String value = "";

			if (param->m_type == eColumnType::DC)
			{
				value = extendDocCircul->docCircul->Params->GetValue(param->getColumnParam());
			}
			else if (param->m_type == eColumnType::IC)
			{
				value = extendDocCircul->infCom->InfComParams->GetValue(param->getColumnParam());
			}
			else
			{
				// todo: проверку на нулл инкапсулировать надама
				if (extendDocCircul->role != NULL)
					value = extendDocCircul->role->GetValue(param->getColumnParam());
			}

			m_View->addValueToDocCirculList(i - offset, j, value);

			param->calcColumnSize(value);
		}


		String Status = extendDocCircul->docCircul->Params->GetValue("Status");

		int Picture=0;
		if (Status == "CorrectEnd") {
			Picture = 1;
		}
		if (Status == "ErrorEnd") {
			Picture = 2;
		}
		if (Status == "Processed") {
			Picture = 0;
		}
		m_View->addStatusImage(i - offset, Picture);
	}

	for (int j = 0; j < m_Model->getDocCirculListColumnCount(); j++)
	{
		lscColumnMainList *param = m_Model->getDocCirculColumnParams(j);
		m_View->setColumnSize(j, param->getMaxValue());
	}
}

//---------------------------------------------------------------------------
#pragma package(smart_init)
