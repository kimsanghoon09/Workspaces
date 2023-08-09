console.log(document.querySelector("h1").innerHTML);
/**
 * @실습문제 : game
 * - start함수 사용자로부터 게임명을 입력받고 게임시작.
 *    - #gameStart 현재시각정보를 hh:mm:ss 형식으로 출력
 *    - #gameUptime 게임소요시각을 hh:mm:ss형식으로 출력. setInterval사용해서 1초마다 출력(intervalId).
 * - end함수 게임종료
 *    - #gameEnd 현재 시각정보를 hh:mm:ss 형식으로 출력
 *    - setInterval을 종료(intervalId)  
 */
const reset = () => {
  document.querySelectorAll(".info").forEach((td) => {
    td.innerHTML = "";
  })
  clearInterval(game.info.intervalId);
};

const start = () => {
  // 0.초기화
  reset();

  // 1.게임명 입력받기
  const title = prompt("게임명을 입력하세요.", "LOL");
  // 2.game객체 startup 메소드 호출
  if(title) {
    game.startUp(title);  
  }
};
const end = () => {
  game.shutDown();
};
const game = {
  title: undefined,
  startUp(title){
    // 3. 현재객체에 title, info.start 기록
    this.title = title;
    this.info.start = Date.now();
    // 4. #info테이블에 게임정보기록
    gameTitle.innerHTML = title;
    gameStart.innerHTML = formatTime(this.info.start);
    // 5. 소요시간 interval처리
    this.info.intervalId = setInterval(() => {
      const diff = Date.now() - this.info.start;
      gameUptime.innerHTML = formatUptime(diff);
    }, 1000);
  },
  shutDown(){
    clearInterval(this.info.intervalId);
    this.info.end = Date.now();
    gameEnd.innerHTML = formatTime(this.info.end);
  },
  info : {
    start : undefined, // 게임 시작 시각(millis)
    end : undefined, // 게임 종료 시각(millis)
    intervalId : undefined
  }
};
// const f = (n) => n < 10 ? "0" + n : n; 
const f = (n) => {
  return n.toString().padStart(2, "0");
};

/**
 * 시간차이(millis)를 시:분:초 형식으로 반환
 */
const formatUptime = (diff) => {
  // console.log(diff); // 밀리초 -> 초 -> 시분초
  const sec = Math.floor(diff / 1000); // 소요시간(초)
  const hour = f(Math.trunc(sec / (60 * 60)));
  const minute = f(Math.trunc(sec % (60 * 60) / 60));
  const second = f(sec % (60 * 60) % 60);
  return `${hour}:${minute}:${second}`; 
};

const formatTime = (millis) => {
  const d = new Date(millis);
  const hour = f(d.getHours());
  const minute = f(d.getMinutes());
  const second = f(d.getSeconds());
  return `${hour}:${minute}:${second}`;
}
