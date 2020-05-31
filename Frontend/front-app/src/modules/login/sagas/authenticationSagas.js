import {put, call, takeLatest} from 'redux-saga/effects';
import {POST_SIGNIN_REQUEST, postSignInError} from '../actions/authenticationActions';
import {loginSaveRequest} from '../../app/actions/appActions'
import {fetchSignIn} from '../../../api/login';

function* signIn(payload) {
  try {
    const response = yield call(fetchSignIn, payload);
    if (response.errorMessage){
      yield put(postSignInError(response));
    }else{
      // если успешный ответ(токен, userId)
      // , то сохраняем их и меняем состояние приложения(authorize)
      yield put(loginSaveRequest(response));
    }
  } catch(error) {
    yield put(postSignInError(error.message))
  }
}

function* signInSagas(payload){
    yield takeLatest(POST_SIGNIN_REQUEST, signIn);
}
export { signInSagas };