object frmSaveParamRoleList: TfrmSaveParamRoleList
  Left = 227
  Top = 108
  BorderStyle = bsDialog
  Caption = #1055#1072#1088#1072#1084#1077#1090#1088#1099' '#1087#1086#1080#1089#1082#1072
  ClientHeight = 179
  ClientWidth = 270
  Color = clBtnFace
  ParentFont = True
  OldCreateOrder = True
  Position = poScreenCenter
  OnShow = FormShow
  DesignSize = (
    270
    179)
  PixelsPerInch = 96
  TextHeight = 13
  object OKBtn: TButton
    Left = 185
    Top = 8
    Width = 75
    Height = 25
    Anchors = [akTop, akRight]
    Caption = 'OK'
    Default = True
    ModalResult = 1
    TabOrder = 0
    OnClick = OKBtnClick
  end
  object CancelBtn: TButton
    Left = 185
    Top = 38
    Width = 75
    Height = 25
    Anchors = [akTop, akRight]
    Cancel = True
    Caption = 'Cancel'
    ModalResult = 2
    TabOrder = 1
    OnClick = CancelBtnClick
  end
  object memoParamList: TMemo
    Left = 8
    Top = 8
    Width = 167
    Height = 161
    Anchors = [akLeft, akTop, akRight, akBottom]
    TabOrder = 2
  end
end
