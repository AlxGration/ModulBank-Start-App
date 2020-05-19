import {put, call, takeLatest} from 'redux-saga/effects';
import {POST_SIGNUP_REQUEST, postSignUpSuccess , postSignUpError} from '../actions/authActions';
import {fetchSignUp} from '../api/registrationAPI';

export function* signUp(payload) {
  try {
    const response = yield call(fetchSignUp, payload);
    if (response.errorMessage){
      yield put(postSignUpError(response));
    }else{
      yield put(postSignUpSuccess());
    }
  } catch(error) {
    yield put(postSignUpError(error.message))
  }
}

function* signUpSagas(payload){
    yield takeLatest(POST_SIGNUP_REQUEST, signUp);
}
export { signUpSagas };