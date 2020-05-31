export const LOGIN_SAVE_REQUEST = 'LOGIN_SAVE_REQUEST';
export const LOGIN_SAVE_SUCCESS = 'LOGIN_SAVE_SUCCESS';
export const LOGOUT_REQUEST = 'LOGOUT_REQUEST';
export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';

export const loginSaveRequest = (data) =>({
    type: LOGIN_SAVE_REQUEST,
    data
});

export const loginSaveSuccess = () =>({
    type: LOGIN_SAVE_SUCCESS,
});

export const logOutRequest = () =>({
    type: LOGOUT_REQUEST
});

export const logOutSuccess = () =>({
    type: LOGOUT_SUCCESS
});