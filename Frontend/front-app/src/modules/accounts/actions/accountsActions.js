export const GET_ACCOUNTS_REQUEST = 'GET_ACCOUNTS_REQUEST';
export const GET_ACCOUNTS_SUCCESS = 'GET_ACCOUNTS_SUCCESS';
export const GET_ACCOUNTS_ERROR = 'GET_ACCOUNTS_ERROR';
export const SET_DISPLAYED_ACCOUNT = 'SET_DISPLAYED_ACCOUNT';

export const getAccountsRequest = () =>({
    type: GET_ACCOUNTS_REQUEST
});

export const getAccountsSuccess = (data) =>({
    type: GET_ACCOUNTS_SUCCESS,
    data
});

export const getAccountsError = (data) =>({
    type: GET_ACCOUNTS_ERROR,
    data
});

export const setDisplayedAccount = (data) =>({
    type: SET_DISPLAYED_ACCOUNT,
    data
});