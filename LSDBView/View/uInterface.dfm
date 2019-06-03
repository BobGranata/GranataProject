object FormInterface: TFormInterface
  Left = 0
  Top = 0
  BorderIcons = [biSystemMenu]
  Caption = #1053#1072#1089#1090#1088#1086#1081#1082#1080' '#1087#1088#1086#1075#1088#1072#1084#1084#1099
  ClientHeight = 532
  ClientWidth = 980
  Color = clBtnFace
  Constraints.MinWidth = 300
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  OldCreateOrder = False
  Position = poOwnerFormCenter
  OnShow = FormShow
  DesignSize = (
    980
    532)
  PixelsPerInch = 96
  TextHeight = 13
  object Button2: TButton
    Left = 8
    Top = 499
    Width = 75
    Height = 25
    Anchors = [akLeft, akBottom]
    Caption = 'OK'
    TabOrder = 0
    OnClick = Button2Click
  end
  object Button3: TButton
    Left = 897
    Top = 499
    Width = 75
    Height = 25
    Anchors = [akRight, akBottom]
    Caption = #1054#1090#1084#1077#1085#1072
    TabOrder = 1
    OnClick = Button3Click
  end
  object Panel1: TPanel
    Left = 2
    Top = 2
    Width = 975
    Height = 485
    Anchors = [akLeft, akTop, akRight, akBottom]
    BevelOuter = bvNone
    TabOrder = 2
    DesignSize = (
      975
      485)
    object TreeView1: TTreeView
      Left = 0
      Top = 0
      Width = 137
      Height = 485
      Anchors = [akLeft, akTop, akBottom]
      HideSelection = False
      Indent = 19
      ReadOnly = True
      RowSelect = True
      ShowRoot = False
      TabOrder = 0
      OnClick = TreeView1Click
      Items.NodeData = {
        03020000003E0000000000000000000000FFFFFFFFFFFFFFFF00000000000000
        000000000001101E044004330430043D04380437043004460438043804200074
        00650073007400320000000000000000000000FFFFFFFFFFFFFFFF0000000000
        00000000000000010A1E0431043D043E0432043B0435043D0438043504}
    end
    object PageControl1: TPageControl
      Left = 143
      Top = 0
      Width = 832
      Height = 485
      ActivePage = RoleListTabSheet
      Anchors = [akLeft, akTop, akRight, akBottom]
      TabOrder = 1
      object RoleListTabSheet: TTabSheet
        Caption = #1054#1088#1075#1072#1085#1080#1079#1072#1094#1080#1080' test'
        ImageIndex = 2
        TabVisible = False
        OnResize = RoleListTabSheetResize
      end
      object TabSheet4: TTabSheet
        Caption = #1054#1073#1085#1086#1074#1083#1077#1085#1080#1077
        ImageIndex = 3
        TabVisible = False
        ExplicitLeft = 0
        ExplicitTop = 0
        ExplicitWidth = 0
        ExplicitHeight = 0
        object GroupBox4: TGroupBox
          Left = 3
          Top = 0
          Width = 562
          Height = 238
          Caption = #1053#1072#1089#1090#1088#1086#1081#1082#1080' '#1087#1088#1086#1082#1089#1080' '#1076#1083#1103' '#1089#1082#1072#1095#1080#1074#1072#1085#1080#1103' '#1072#1074#1090#1086#1084#1072#1090#1080#1095#1077#1089#1082#1086#1075#1086' '#1086#1073#1085#1086#1074#1083#1077#1085#1080#1103
          TabOrder = 0
          DesignSize = (
            562
            238)
          object Label1: TLabel
            Left = 19
            Top = 61
            Width = 35
            Height = 13
            Caption = #1040#1076#1088#1077#1089':'
          end
          object Label2: TLabel
            Left = 19
            Top = 96
            Width = 29
            Height = 13
            Caption = #1055#1086#1088#1090':'
          end
          object Label3: TLabel
            Left = 19
            Top = 162
            Width = 19
            Height = 13
            Caption = #1048#1084#1103
          end
          object Label4: TLabel
            Left = 19
            Top = 197
            Width = 37
            Height = 13
            Caption = #1055#1072#1088#1086#1083#1100
          end
          object CheckBox1: TCheckBox
            Left = 19
            Top = 27
            Width = 526
            Height = 17
            Anchors = [akLeft, akTop, akRight]
            Caption = #1048#1089#1087#1086#1083#1100#1079#1086#1074#1072#1090#1100' '#1087#1088#1086#1082#1089#1080'-'#1089#1077#1088#1074#1077#1088' '#1076#1083#1103' '#1089#1082#1072#1095#1080#1074#1072#1085#1080#1103' '#1086#1073#1085#1086#1074#1083#1077#1085#1080#1081
            TabOrder = 0
            OnClick = CheckBox1Click
          end
          object CheckBox2: TCheckBox
            Left = 19
            Top = 128
            Width = 526
            Height = 17
            Anchors = [akLeft, akTop, akRight]
            Caption = #1048#1089#1087#1086#1083#1100#1079#1086#1074#1072#1090#1100' '#1072#1091#1090#1077#1085#1090#1080#1092#1080#1082#1072#1094#1080#1102' '#1076#1083#1103' '#1087#1088#1086#1082#1089#1080'-'#1089#1077#1088#1074#1077#1088#1072
            TabOrder = 1
            OnClick = CheckBox2Click
          end
          object Edit1: TEdit
            Left = 77
            Top = 58
            Width = 468
            Height = 21
            Anchors = [akLeft, akTop, akRight]
            TabOrder = 2
          end
          object Edit2: TEdit
            Left = 77
            Top = 93
            Width = 468
            Height = 21
            Anchors = [akLeft, akTop, akRight]
            TabOrder = 3
          end
          object Edit3: TEdit
            Left = 77
            Top = 159
            Width = 468
            Height = 21
            Anchors = [akLeft, akTop, akRight]
            TabOrder = 4
          end
          object Edit4: TEdit
            Left = 77
            Top = 194
            Width = 468
            Height = 21
            Anchors = [akLeft, akTop, akRight]
            PasswordChar = '*'
            TabOrder = 5
          end
        end
      end
    end
  end
  object OpenDialog1: TOpenDialog
    Filter = #1053#1072#1089#1090#1088#1086#1077#1095#1085#1099#1077' '#1092#1072#1081#1083#1099'|Param*.xml|'#1042#1089#1077' '#1092#1072#1081#1083#1099'|*.*'
    Options = [ofHideReadOnly, ofAllowMultiSelect, ofEnableSizing]
    Left = 136
    Top = 488
  end
end
