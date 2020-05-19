//----------------------------------------------------------
// Sign In
//----------------------------------------------------------

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

//----------------------------------------------------------
// Sign Up
//----------------------------------------------------------

export const POST_SIGNUP_REQUEST = 'POST_SIGNUP_REQUEST';
export const POST_SIGNUP_SUCCESS = 'POST_SIGNUP_SUCCESS';
export const POST_SIGNUP_ERROR = 'POST_SIGNUP_ERROR';

export const postSignUpRequest = (user) =>({
    type: POST_SIGNUP_REQUEST,
    user
});

export const postSignUpSuccess = () =>({
    type: POST_SIGNUP_SUCCESS
});

export const postSignUpError = (data) =>({
    type: POST_SIGNUP_ERROR,
    data
});