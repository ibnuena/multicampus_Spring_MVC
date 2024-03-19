
 function check(){
 	// 아이디는 영문자로 시작하고, 영문자, 숫자, _, !, #만 4~8자 이내
        if(!isKor(mf.name)){
            alert('이름은 한글만 입력 가능 합니다');
            mf.name.select();
            return;
        }
        if(!isUserid(mf.userid)){
            alert('아이디는 영문자로 시작하고, 영문자, 숫자, _, !, #만 4~8자 이내');
            mf.userid.select();
            return;
 	    }	
        if(!isPassword(mf.pwd)){
            alert('비밀번호는 영문자, 숫자, . _ !만 사용 가능하며, 4~8자로 만들어야 합니다');
            mf.pwd.select;
            return;
        }
        if(mf.pwd.value != mf.pwd2.value){
            alert('비밀번호 확인이 일치하지 않습니다');
            mf.pwd2.select();
            return;
        }
        if(!isMobile(mf.hp1, mf.hp2, mf.hp3)){
            alert('전화번호 형식이 올바르지 않습니다');
            mf.hp2.select();
            return;
        }
 	    mf.submit();
    }
 
    /*
        ^ : 시작
        $ : 끝
        가-힣 : 한글
        + : 한개 이상
        \s : 공백문자 허용
    */
    function isKor(input){
        let str = input.value;
        let parttern = /^[가-힣\s]+$/;
        let b = parttern.test(str);
        // alert(b);

        return b;
    }

    // flag 문자 : i(ignore case), g(global), m(multiline)
    function isUserid(input){
 	    // alert(input.value);
        let str = input.value;
        // let pattern = new RegExp(/multi/i);
        let pattern = /^[A-Za-z]{1}[A-Za-z0-9_!#]{3,7}$/;
        let b = pattern.test(str);
        // alert('b : ' + b);
 	    return b;
    }

    // \w : 알파벳 대소문자, 숫자 허용
    function isPassword(input){
        let str = input.value;
        let parttern = /^[\w!_.]{4,8}$/;
        let b = parttern.test(str);
        // alert(b);

        return b;
    }

    // \b : 단어의 경계
    function isMobile(input1, input2, input3){
        let str = input1.value + "-" + input2.value + "-" + input3.value;
        // alert(str);
        let parttern = /\b(010|011|017)[-]\d{3,4}[-]\d{4}\b/;
        let b = parttern.test(str);
        // alert(b);
        return b;
    }