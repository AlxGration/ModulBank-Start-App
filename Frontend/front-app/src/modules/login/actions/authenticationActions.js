export const POST_SIGNIN_REQUEST = 'POST_SIGNIN_REQUEST';
export const POST_SIGNIN_SUCCESS = 'POST_SIGNIN_SUCCESS';
export const POST_SIGNIN_ERROR = 'POST_SIGNIN_ERROR';

export const postSignInRequest = (user) =>({
    type: POST_SIGNIN_REQUEST,
    user
});

export const postSignInSuccess = (data) =>({
    type: POST_SIGNIN_SUCCESS,
    data
});

export const postSignInError = (data) =>({
    type: POST_SIGNIN_ERROR,
    data
});