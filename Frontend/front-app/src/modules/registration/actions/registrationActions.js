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