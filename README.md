README for bluepass
==========================
모든 factory, service의 이름은 pascal case

그 외의 이름은 모두 camel case

=================파일명=================

script/app - 웹을 구축하고 있는 실질적인 소스들

script/app/**.* - 웹의 페이지별로 구분되어 있으면 구성은 다음과 같음

페이지명/페이지명.controller.js - 페이지의 컨트롤러

페이지명/페이지명.html - 페이지의 html

페이지명/페이지명.js - 페이지의 state(angular-ui-router사용)


script/component - 위의 소스들의 부가요소들

script/component/api명/api명.factory.js || script/component/api명/resource/api명.factory.js - api를 호출하는 resource들

script/component/util/**.* - 웹의 비즈니스 로직 || 이벤트 로직을 담당하는 directive, factory, service가 모여있음


=================소스 스타일=================

기본적인 스타일은 https://github.com/johnpapa/angular-styleguide 을 따름

api명.factory.js의 resource들을 사용할 땐 get+api명 || 기능으로 네이밍한 함수를 만들어 사용
