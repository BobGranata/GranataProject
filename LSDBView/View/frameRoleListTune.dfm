object frmRoleListTune: TfrmRoleListTune
  Left = 0
  Top = 0
  Width = 451
  Height = 304
  Align = alClient
  AutoSize = True
  TabOrder = 0
  DesignSize = (
    451
    304)
  object Label6: TLabel
    Left = 3
    Top = 11
    Width = 49
    Height = 13
    Caption = #1058#1080#1087' '#1088#1086#1083#1080':'
  end
  object listRoleType: TListBox
    Left = 3
    Top = 30
    Width = 118
    Height = 163
    ItemHeight = 13
    TabOrder = 0
    OnClick = listRoleTypeClick
  end
  object btnAddRole: TButton
    Left = 127
    Top = 269
    Width = 77
    Height = 25
    Anchors = [akLeft, akBottom]
    Caption = #1044#1086#1073#1072#1074#1080#1090#1100
    TabOrder = 1
    OnClick = btnAddRoleClick
  end
  object btnEditRole: TButton
    Left = 210
    Top = 269
    Width = 77
    Height = 25
    Anchors = [akLeft, akBottom]
    Caption = #1048#1079#1084#1077#1085#1080#1090#1100
    TabOrder = 2
    OnClick = btnEditRoleClick
  end
  object edSearch: TEdit
    Left = 224
    Top = 3
    Width = 168
    Height = 21
    Anchors = [akLeft, akTop, akRight]
    TabOrder = 3
    OnChange = edSearchChange
    OnKeyDown = edSearchKeyDown
  end
  object comboSearch: TComboBox
    Left = 158
    Top = 3
    Width = 60
    Height = 21
    Style = csDropDownList
    TabOrder = 4
    OnClick = comboSearchClick
    OnKeyDown = comboSearchKeyDown
  end
  object btnSearch: TButton
    Left = 398
    Top = 3
    Width = 50
    Height = 22
    Anchors = [akTop, akRight]
    Caption = #1055#1086#1080#1089#1082
    TabOrder = 5
  end
  object roleList: TListView
    Left = 127
    Top = 30
    Width = 321
    Height = 233
    Anchors = [akLeft, akTop, akRight, akBottom]
    Columns = <>
    DoubleBuffered = True
    ReadOnly = True
    RowSelect = True
    ParentDoubleBuffered = False
    TabOrder = 6
    ViewStyle = vsReport
    OnDblClick = roleList1DblClick
  end
  object Panel1: TPanel
    Left = 248
    Top = 265
    Width = 203
    Height = 37
    Anchors = [akRight, akBottom]
    TabOrder = 7
    object lbPageCount: TLabel
      Left = 87
      Top = 12
      Width = 4
      Height = 13
      Caption = '/'
    end
    object btnNextPage: TButton
      Left = 120
      Top = 6
      Width = 25
      Height = 25
      Caption = '>'
      TabOrder = 0
      OnClick = btnNextPageClick
    end
    object edCurrentPage: TEdit
      Left = 40
      Top = 8
      Width = 41
      Height = 21
      NumbersOnly = True
      TabOrder = 1
      Text = '1'
      OnChange = edCurrentPageChange
    end
    object btnPreviousPage: TButton
      Left = 9
      Top = 6
      Width = 25
      Height = 25
      Caption = '<'
      TabOrder = 2
      OnClick = btnPreviousPageClick
    end
    object cbPageOnScreen: TComboBox
      Left = 151
      Top = 8
      Width = 50
      Height = 21
      TabOrder = 3
      OnClick = cbPageOnScreenClick
    end
  end
  object btnVisiParamEdit: TButton
    Left = 127
    Top = 3
    Width = 25
    Height = 21
    Caption = 'S'
    TabOrder = 8
    OnClick = btnVisiParamEditClick
  end
end
