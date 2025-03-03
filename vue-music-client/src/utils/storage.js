// 用户信息持久化
export const setUserInfo = (userInfo) => {
  localStorage.setItem('userInfo', JSON.stringify(userInfo));
}

export const getUserInfo = () => {
  const userInfo = localStorage.getItem('userInfo');
  return userInfo ? JSON.parse(userInfo) : null;
}

export const clearUserInfo = () => {
  localStorage.removeItem('userInfo');
}

export const setLoginStatus = (status) => {
  localStorage.setItem('loginStatus', status);
}

export const getLoginStatus = () => {
  return localStorage.getItem('loginStatus') === 'true';
}

export const clearLoginStatus = () => {
  localStorage.removeItem('loginStatus');
}

// 添加一个重置所有存储的函数
export const resetAllStorage = () => {
  clearUserInfo();
  clearLoginStatus();
  // 可能还有其他需要清除的存储项
} 