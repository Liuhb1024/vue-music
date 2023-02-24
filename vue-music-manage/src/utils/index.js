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