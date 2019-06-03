object frmDocumentList: TfrmDocumentList
  Left = 0
  Top = 0
  Align = alClient
  BorderStyle = bsNone
  Caption = #1044#1086#1082#1091#1084#1077#1085#1090#1099
  ClientHeight = 338
  ClientWidth = 956
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object pnlBase: TPanel
    Left = 0
    Top = 0
    Width = 956
    Height = 338
    Align = alClient
    TabOrder = 0
    object sPanel1: TPanel
      Left = 1
      Top = 1
      Width = 40
      Height = 336
      Align = alLeft
      TabOrder = 0
      object sToolBar1: TToolBar
        Left = 1
        Top = 1
        Width = 38
        Height = 334
        Align = alClient
        ButtonHeight = 38
        ButtonWidth = 39
        DoubleBuffered = True
        Images = dmViewer.imMainMenuEnable
        ParentDoubleBuffered = False
        TabOrder = 0
        object btnView: TToolButton
          Left = 0
          Top = 0
          Caption = 'btnView'
          ImageIndex = 4
          Wrap = True
        end
        object btnResend: TToolButton
          Left = 0
          Top = 38
          Caption = 'btnResend'
          ImageIndex = 0
          OnClick = btnResendClick
        end
        object ListView1: TListView
          Left = 39
          Top = 38
          Width = 250
          Height = 38
          Columns = <>
          TabOrder = 0
        end
      end
    end
    object lvDocumentsList: TListView
      Left = 41
      Top = 1
      Width = 914
      Height = 336
      Align = alClient
      Columns = <>
      DoubleBuffered = True
      ReadOnly = True
      RowSelect = True
      ParentDoubleBuffered = False
      SmallImages = dmViewer.ilDocument
      TabOrder = 1
      ViewStyle = vsReport
      OnDblClick = lvDocumentsListDblClick
    end
  end
end
