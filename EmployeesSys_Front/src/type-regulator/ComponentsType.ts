/**
 * commonent types
 */

export interface AdminManageSearchFieldItem {
  label: string
  key: string
  type: 'input' | 'select' | 'date'
  options?: Array<{ label: string, value: any }>,
  default?: string | number,
}
export type AdminManageSearchFieldItems = Array<AdminManageSearchFieldItem>;

export interface TableColumn {
  key: string,
  label: string,
  sorted?: boolean,
}
export type TableColumns = Array<TableColumn>;

export type FormalDatum = { [key: string]: string | number };
export type FormalData = Array<FormalDatum>;

export interface FormItem {
  key: string,
  label: string,
  type: 'input' | 'select' | 'multiple-select',
  regulator?: RegExp, // input regulated
  autofill?: string, // autofill source
  dropdownOpt?: { 
    key: string,
    label: string,
    value: string | number
  }[],
}
export type FormItems = Array<FormItem>;

export type RowData = Map<String, String | Number | Array<String | Number>>

/**
 * special types
 */

export interface ParkInfoItems {
  id: string
  name: string
  address: string
  maxiumn_value: number
  maxiumn_height: number
  legal: string
  phone: string
  cost: number
  lng: number
  lat: number
  descript: string
  available: number
  updated_time?: string
}