import FileSaver from 'file-saver'
import XLSX from 'xlsx'

export function exportExcelAllField(excelFileName, jsonData) {
  const ws = XLSX.utils.json_to_sheet(jsonData)
  // const ws2 = XLSX.utils.json_to_sheet(jsonData)
  const wb = XLSX.utils.book_new()
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1')
  XLSX.writeFile(wb, excelFileName)
}

export function exportExcelTableField(excelFileName, tableId) {
  const wb = XLSX.utils.table_to_book(document.querySelector('#' + tableId))
  const wbout = XLSX.write(wb, {
    bookType: 'xlsx',
    bookSST: true,
    type: 'array'
  })
  try {
    FileSaver.saveAs(
      new Blob([wbout], { type: 'application/octet-stream' }),
      excelFileName
    )
  } catch (e) {
    if (typeof console !== 'undefined') console.log(e, wbout)
  }
}
