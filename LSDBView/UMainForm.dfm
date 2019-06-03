object frmMain: TfrmMain
  Left = 0
  Top = 0
  Caption = 'DBViewer'
  ClientHeight = 639
  ClientWidth = 1238
  Color = clBtnFace
  Constraints.MinHeight = 300
  Constraints.MinWidth = 500
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  Position = poDesigned
  WindowState = wsMaximized
  OnActivate = FormActivate
  PixelsPerInch = 96
  TextHeight = 13
  object splitterDcAndDoc: TSplitter
    Left = 0
    Top = 450
    Width = 1238
    Height = 5
    Cursor = crVSplit
    Align = alBottom
  end
  object panelDocumentList: TPanel
    Left = 0
    Top = 455
    Width = 1238
    Height = 184
    Align = alBottom
    TabOrder = 0
  end
  object panelExtendDc: TPanel
    Left = 0
    Top = 0
    Width = 1238
    Height = 450
    Align = alClient
    TabOrder = 1
    object panelDocCircul: TPanel
      Left = 45
      Top = 168
      Width = 1193
      Height = 282
      Align = alCustom
      Anchors = [akLeft, akTop, akRight, akBottom]
      TabOrder = 0
      DesignSize = (
        1193
        282)
      object lvDocCirculList: TListView
        Left = 0
        Top = 3
        Width = 1192
        Height = 246
        Align = alCustom
        Anchors = [akLeft, akTop, akRight, akBottom]
        Columns = <>
        DoubleBuffered = True
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'Tahoma'
        Font.Style = []
        ReadOnly = True
        RowSelect = True
        ParentDoubleBuffered = False
        ParentFont = False
        SmallImages = DataModuleDcImage.ilDocCirculIcons
        TabOrder = 0
        ViewStyle = vsReport
        OnClick = lvDocCirculListClick
        OnCustomDrawItem = lvDocCirculListCustomDrawItem
        OnCustomDrawSubItem = lvDocCirculListCustomDrawSubItem
        OnDblClick = lvDocCirculListDblClick
        OnKeyDown = lvDocCirculListKeyDown
        OnKeyUp = lvDocCirculListKeyUp
      end
      object pnlPaging: TPanel
        Left = 902
        Top = 251
        Width = 289
        Height = 30
        Anchors = [akRight, akBottom]
        BevelOuter = bvNone
        TabOrder = 1
        object lbPageCount: TLabel
          Left = 110
          Top = 8
          Width = 4
          Height = 13
          Caption = '/'
        end
        object btnNextPage: TButton
          Left = 170
          Top = 3
          Width = 25
          Height = 25
          Caption = '>'
          TabOrder = 0
          OnClick = btnNextPageClick
        end
        object btnPreviousPage: TButton
          Left = 34
          Top = 3
          Width = 25
          Height = 25
          Caption = '<'
          TabOrder = 1
          OnClick = btnPreviousPageClick
        end
        object btnLastPage: TButton
          Left = 201
          Top = 3
          Width = 25
          Height = 25
          Caption = '>>'
          TabOrder = 2
          OnClick = btnLastPageClick
        end
        object btnFirstPage: TButton
          Left = 3
          Top = 3
          Width = 25
          Height = 25
          Caption = '<<'
          TabOrder = 3
          OnClick = btnFirstPageClick
        end
        object cbPageOnScreen: TComboBox
          Left = 232
          Top = 5
          Width = 50
          Height = 21
          TabOrder = 4
          OnChange = cbPageOnScreenChange
        end
        object edCurrentPage: TEdit
          Left = 65
          Top = 5
          Width = 41
          Height = 21
          NumbersOnly = True
          TabOrder = 5
          Text = '0'
          OnChange = edCurrentPageChange
        end
      end
    end
    object panelFilter: TPanel
      Left = 45
      Top = 0
      Width = 1193
      Height = 156
      Align = alCustom
      Anchors = [akLeft, akTop, akRight]
      BevelOuter = bvNone
      TabOrder = 1
      DesignSize = (
        1193
        156)
      object lbDcDate: TLabel
        Left = 26
        Top = 90
        Width = 80
        Height = 13
        Caption = #1044#1072#1090#1072' '#1089#1086#1079#1076#1072#1085#1080#1103':'
      end
      object lbBeginDate: TLabel
        Left = 216
        Top = 90
        Width = 18
        Height = 13
        Caption = #1054#1090':'
      end
      object lbEndDate: TLabel
        Left = 356
        Top = 90
        Width = 18
        Height = 13
        Caption = #1044#1086':'
      end
      object lbDocCirculType: TLabel
        Left = 812
        Top = 63
        Width = 22
        Height = 13
        Caption = #1058#1080#1087':'
      end
      object lbDocCurculStatus: TLabel
        Left = 651
        Top = 63
        Width = 40
        Height = 13
        Caption = #1057#1090#1072#1090#1091#1089':'
      end
      object lbDocument: TLabel
        Left = 51
        Top = 117
        Width = 54
        Height = 13
        Caption = #1044#1086#1082#1091#1084#1077#1085#1090':'
      end
      object lbDocumentStatus: TLabel
        Left = 651
        Top = 117
        Width = 40
        Height = 13
        Caption = #1057#1090#1072#1090#1091#1089':'
      end
      object btnFindDocCircul: TButton
        Left = 1032
        Top = 113
        Width = 147
        Height = 23
        Anchors = [akTop, akRight]
        Caption = #1055#1086#1082#1072#1079#1072#1090#1100
        TabOrder = 0
        OnClick = btnFindDocCirculClick
      end
      object cbDatePeriodFilter: TComboBox
        Left = 112
        Top = 87
        Width = 98
        Height = 21
        Style = csDropDownList
        TabOrder = 1
        OnChange = cbDatePeriodFilterChange
      end
      object dtpBeginDate: TDateTimePicker
        Left = 241
        Top = 87
        Width = 106
        Height = 21
        Date = 43411.641458796290000000
        Time = 43411.641458796290000000
        TabOrder = 2
      end
      object dtpEndDate: TDateTimePicker
        Left = 381
        Top = 87
        Width = 106
        Height = 21
        Date = 43411.641458796290000000
        Time = 43411.641458796290000000
        TabOrder = 3
      end
      object cbDocCirculType: TComboBox
        Left = 840
        Top = 60
        Width = 145
        Height = 21
        TabOrder = 4
        Text = #1042#1089#1077' '#1087#1086#1076#1088#1103#1076
      end
      object tbDocCirculType: TToolBar
        Left = 6
        Top = 136
        Width = 1178
        Height = 20
        Align = alCustom
        Anchors = [akLeft, akTop, akRight]
        ButtonHeight = 21
        ButtonWidth = 65
        Caption = 'tbDocCirculType'
        DoubleBuffered = True
        EdgeBorders = [ebTop]
        Flat = False
        ParentDoubleBuffered = False
        ShowCaptions = True
        TabOrder = 5
        Wrapable = False
      end
      object Panel1: TPanel
        Left = 10
        Top = 0
        Width = 631
        Height = 86
        BevelOuter = bvNone
        TabOrder = 6
        DesignSize = (
          631
          86)
        object lbDcCaption: TLabel
          Left = 0
          Top = 63
          Width = 96
          Height = 13
          Caption = #1044#1086#1082#1091#1084#1077#1085#1090#1086#1086#1073#1086#1088#1086#1090':'
        end
        object lbInfComCaption: TLabel
          Left = 24
          Top = 36
          Width = 71
          Height = 13
          Caption = #1053#1072#1087#1088#1072#1074#1083#1077#1085#1080#1077':'
        end
        object lbRoleCaption: TLabel
          Left = 24
          Top = 9
          Width = 72
          Height = 13
          Caption = #1054#1090#1087#1088#1072#1074#1080#1090#1077#1083#1100':'
        end
        object cbDcParam: TComboBox
          Left = 102
          Top = 60
          Width = 98
          Height = 21
          Style = csDropDownList
          TabOrder = 0
        end
        object cbInfComParam: TComboBox
          Left = 102
          Top = 33
          Width = 98
          Height = 21
          Style = csDropDownList
          TabOrder = 1
        end
        object edDcParamText: TEdit
          Left = 206
          Top = 60
          Width = 423
          Height = 21
          Anchors = [akLeft, akTop, akRight]
          TabOrder = 2
        end
        object edInfComParam: TEdit
          Left = 206
          Top = 33
          Width = 423
          Height = 21
          Anchors = [akLeft, akTop, akRight]
          TabOrder = 3
        end
        object cbSearchField: TComboBox
          Left = 102
          Top = 6
          Width = 98
          Height = 21
          Style = csDropDownList
          TabOrder = 4
        end
        object edSearchValue: TEdit
          Left = 206
          Top = 6
          Width = 423
          Height = 21
          Anchors = [akLeft, akTop, akRight]
          TabOrder = 5
        end
      end
      object cbDocCirculStatus: TComboBox
        Left = 697
        Top = 60
        Width = 104
        Height = 21
        TabOrder = 7
      end
      object cbDocumentParam: TComboBox
        Left = 112
        Top = 114
        Width = 98
        Height = 21
        Style = csDropDownList
        TabOrder = 8
      end
      object cbDocumentParamText: TEdit
        Left = 216
        Top = 114
        Width = 423
        Height = 21
        TabOrder = 9
      end
      object cbDocumentStatus: TComboBox
        Left = 697
        Top = 114
        Width = 104
        Height = 21
        TabOrder = 10
      end
    end
    object tbarMainMenu: TToolBar
      Left = 0
      Top = 0
      Width = 39
      Height = 494
      Align = alCustom
      AutoSize = True
      ButtonHeight = 52
      ButtonWidth = 39
      Caption = 'tbarMainMenu'
      Color = clBtnFace
      DoubleBuffered = False
      GradientEndColor = clSkyBlue
      GradientStartColor = clGradientActiveCaption
      Images = dmViewer.imMainMenuEnable
      ParentColor = False
      ParentDoubleBuffered = False
      ShowCaptions = True
      TabOrder = 2
      Transparent = True
      Wrapable = False
      object btnOpenBase: TToolButton
        Left = 0
        Top = 0
        ImageIndex = 6
        Wrap = True
        OnClick = btnOpenBaseClick
      end
      object btnRefreshDB: TToolButton
        Left = 0
        Top = 52
        ImageIndex = 5
        Wrap = True
        OnClick = btnRefreshDBClick
      end
      object ToolButton7: TToolButton
        Left = 0
        Top = 104
        Hint = #1055#1088#1086#1089#1084#1086#1090#1088' '#1076#1086#1082#1091#1084#1077#1085#1090#1072' '#1080#1083#1080' '#1076#1086#1082#1091#1084#1077#1085#1090#1086#1086#1073#1086#1088#1086#1090#1072
        Enabled = False
        ImageIndex = 4
        ParentShowHint = False
        Wrap = True
        ShowHint = True
      end
    end
  end
  object MainMenu1: TMainMenu
    Left = 760
    Top = 168
    object N1: TMenuItem
      Caption = #1060#1072#1081#1083
      object N4: TMenuItem
        Caption = #1042#1099#1093#1086#1076
        OnClick = N4Click
      end
    end
    object N6: TMenuItem
      Caption = #1044#1077#1081#1089#1090#1074#1080#1103
      object N13: TMenuItem
        Caption = #1055#1088#1086#1089#1084#1086#1090#1088
        Enabled = False
      end
      object N19: TMenuItem
        Caption = '-'
      end
      object N27: TMenuItem
        Caption = #1055#1086#1082#1072#1079#1072#1090#1100' '#1085#1086#1074#1099#1077' '#1076#1086#1082#1091#1084#1077#1085#1090#1086#1086#1073#1086#1088#1086#1090#1099
      end
    end
    object N12: TMenuItem
      Caption = #1053#1072#1089#1090#1088#1086#1081#1082#1080
      object N2: TMenuItem
        Caption = #1048#1085#1090#1077#1088#1092#1077#1081#1089
        OnClick = N2Click
      end
      object test1: TMenuItem
        Caption = 'test'
        Visible = False
      end
      object N25: TMenuItem
        Caption = #1042#1099#1075#1088#1091#1079#1080#1090#1100' '#1045#1082#1072#1090#1089#1082#1080#1077' '#1089#1077#1088#1090#1099
        OnClick = N25Click
      end
      object N31: TMenuItem
        Caption = #1042#1099#1075#1088#1091#1079#1080#1090#1100' '#1086#1096#1080#1073#1086#1095#1085#1099#1077' '#1082#1086#1085#1090#1077#1081#1085#1077#1088#1099
        OnClick = N31Click
      end
      object NListPfrRegistration: TMenuItem
        Caption = #1057#1087#1080#1089#1086#1082' '#1088#1077#1075#1080#1089#1090#1088#1072#1094#1080#1081' '#1055#1060#1056
        OnClick = NListPfrRegistrationClick
      end
    end
    object N20: TMenuItem
      Caption = #1048#1085#1089#1090#1088#1091#1084#1077#1085#1090#1099
      object N32: TMenuItem
        Caption = #1057#1087#1080#1089#1086#1082' '#1086#1090#1095#1105#1090#1086#1074' '#1073#1077#1079' '#1086#1090#1074#1077#1090#1072' ('#1060#1053#1057')'
        OnClick = N32Click
      end
      object N24: TMenuItem
        Caption = #1057#1087#1080#1089#1086#1082' '#1086#1090#1095#1077#1090#1086#1074' '#1073#1077#1079' '#1086#1090#1074#1077#1090#1072'  ('#1055#1060#1056')'
        OnClick = N24Click
      end
      object N14: TMenuItem
        Caption = #1040#1073#1086#1085#1077#1085#1090#1099' ('#1060#1053#1057')'
        OnClick = N14Click
      end
      object N15: TMenuItem
        Caption = #1040#1073#1086#1085#1077#1085#1090#1099' ('#1055#1060#1056')'
        OnClick = N15Click
      end
      object N11: TMenuItem
        Caption = #1048#1089#1090#1077#1082#1072#1102#1097#1080#1077' '#1089#1077#1088#1090#1080#1092#1080#1082#1072#1090#1099' ('#1060#1053#1057')'
        OnClick = N11Click
      end
      object N7: TMenuItem
        Caption = #1048#1089#1090#1077#1082#1072#1102#1097#1080#1077' '#1089#1077#1088#1090#1080#1092#1080#1082#1072#1090#1099' ('#1055#1060#1056')'
        OnClick = N7Click
      end
    end
    object N16: TMenuItem
      Caption = #1055#1086#1084#1086#1097#1100
      object N17: TMenuItem
        Caption = #1054' '#1087#1088#1086#1075#1088#1072#1084#1084#1077'...'
      end
      object N21: TMenuItem
        Caption = '-'
      end
      object N22: TMenuItem
        Caption = #1055#1086#1084#1086#1097#1100
        ShortCut = 112
        OnClick = N22Click
      end
    end
  end
  object PopupMenu1: TPopupMenu
    Left = 840
    Top = 168
    object mnTreeExpand2: TMenuItem
      Caption = #1056#1072#1079#1074#1077#1088#1085#1091#1090#1100' '#1074#1089#1077
    end
    object mnTreeUnExpand2: TMenuItem
      Caption = #1057#1074#1077#1088#1085#1091#1090#1100' '#1074#1089#1077
    end
    object N44: TMenuItem
      Caption = '-'
    end
    object N38: TMenuItem
      Caption = #1055#1086#1082#1072#1079#1072#1090#1100' '#1085#1086#1074#1099#1077' '#1076#1086#1082#1091#1084#1077#1085#1090#1086#1086#1073#1086#1088#1086#1090#1099
    end
  end
end
