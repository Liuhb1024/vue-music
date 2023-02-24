/**
 * 将秒数格式化为字符串
 * @param {Number} seconds 
 * @returns format as "00:00"
 */
export const getFormattedTimeStr = (seconds) => {
    let str = ''
    let minute = parseInt(seconds / 60)
    let second = seconds % 60
    minute = (minute >= 10 ? '' : '0') + minute + ':'
    second = (second >= 10 ? '' : '0') + second
    str = minute + second
    return str
}

/**
 * @description 将date转为字符串：yyyy-MM-dd格式
 * @param {Date} date
 * @returns String
 */
export const dateToString = (date) => {
	var Y = date.getFullYear();
	var M = date.getMonth() + 1;
	var D = date.getDate();
	return Y + '-' + (M < 10 ? ('0' + M) : M) + '-' + (D < 10 ? ('0' + D) : D);
}