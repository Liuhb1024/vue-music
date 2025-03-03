<template>
  <div class="not-found">
    <div class="content">
      <!-- 音符动画装饰 -->
      <div class="music-notes">
        <span v-for="n in 6" :key="n" class="note" :class="`note-${n}`">♪</span>
      </div>

      <!-- 主要内容 -->
      <div class="main-content">
        <h1>4<span class="zero">0</span>4</h1>
        <p class="message">Oops! 看起来音乐走丢了~</p>
        <div class="vinyl-record">
          <div class="record-circle"></div>
          <div class="record-center"></div>
        </div>
        <router-link to="/" class="home-button">
          返回首页
          <i class="el-icon-right"></i>
        </router-link>
      </div>

      <!-- 波浪动画 -->
      <div class="waves">
        <div class="wave wave1"></div>
        <div class="wave wave2"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '404NotFound'
}
</script>

<style lang="scss" scoped>
.not-found {
  min-height: 100vh;
  background: linear-gradient(135deg, #2d3a4b 0%, #1e2a3a 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  position: relative;

  .content {
    text-align: center;
    position: relative;
    z-index: 1;
    padding: 40px;
  }

  // 音符动画
  .music-notes {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;

    .note {
      position: absolute;
      color: rgba(103, 195, 255, 0.2);
      font-size: 30px;
      animation: floatNote 3s ease-in-out infinite;

      &.note-1 {
        left: 15%;
        top: 20%;
        font-size: 35px;
      }

      &.note-2 {
        left: 35%;
        top: 40%;
        font-size: 28px;
        animation-delay: 0.5s;
      }

      &.note-3 {
        left: 55%;
        top: 25%;
        font-size: 32px;
        animation-delay: 1s;
      }

      &.note-4 {
        left: 75%;
        top: 45%;
        font-size: 30px;
        animation-delay: 1.5s;
      }

      &.note-5 {
        left: 85%;
        top: 30%;
        font-size: 25px;
        animation-delay: 2s;
      }

      &.note-6 {
        left: 25%;
        top: 60%;
        font-size: 38px;
        animation-delay: 2.5s;
      }
    }
  }

  // 主要内容样式
  .main-content {
    position: relative;
    z-index: 2;

    h1 {
      font-size: 120px;
      color: #ffffff;
      margin: 0;
      font-weight: 700;
      text-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
      letter-spacing: 15px;

      .zero {
        display: inline-block;
        animation: rotate 10s linear infinite;
      }
    }

    .message {
      color: #67c3ff;
      font-size: 24px;
      margin: 20px 0 40px;
      text-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    }

    // 唱片效果
    .vinyl-record {
      width: 200px;
      height: 200px;
      margin: 40px auto;
      position: relative;
      animation: float 6s ease-in-out infinite;

      .record-circle {
        width: 100%;
        height: 100%;
        background: linear-gradient(45deg, #000 0%, #333 100%);
        border-radius: 50%;
        position: absolute;
        animation: rotate 20s linear infinite;

        &::after {
          content: '';
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: repeating-radial-gradient(
            circle at center,
            transparent 0,
            transparent 5px,
            rgba(255, 255, 255, 0.1) 5px,
            rgba(255, 255, 255, 0.1) 7px
          );
          border-radius: 50%;
        }
      }

      .record-center {
        width: 50px;
        height: 50px;
        background: #67c3ff;
        border-radius: 50%;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border: 8px solid #fff;
        box-shadow: 0 0 20px rgba(103, 195, 255, 0.5);
      }
    }

    // 返回按钮
    .home-button {
      display: inline-flex;
      align-items: center;
      padding: 12px 30px;
      background: #67c3ff;
      color: #fff;
      text-decoration: none;
      border-radius: 25px;
      font-size: 16px;
      font-weight: 500;
      transition: all 0.3s ease;
      box-shadow: 0 5px 15px rgba(103, 195, 255, 0.3);

      i {
        margin-left: 8px;
        transition: transform 0.3s ease;
      }

      &:hover {
        background: #409eff;
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(103, 195, 255, 0.4);

        i {
          transform: translateX(5px);
        }
      }
    }
  }

  // 波浪动画
  .waves {
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 200px;
    overflow: hidden;

    .wave {
      position: absolute;
      bottom: 0;
      width: 200%;
      height: 100%;
      background-repeat: repeat-x;
      animation: wave 10s linear infinite;

      &.wave1 {
        background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="%234099FF" fill-opacity="0.2" d="M0,192L48,197.3C96,203,192,213,288,192C384,171,480,117,576,101.3C672,85,768,107,864,128C960,149,1056,171,1152,165.3C1248,160,1344,128,1392,112L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
      }

      &.wave2 {
        background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="%2367C3FF" fill-opacity="0.15" d="M0,160L48,144C96,128,192,96,288,106.7C384,117,480,171,576,186.7C672,203,768,181,864,170.7C960,160,1056,160,1152,138.7C1248,117,1344,75,1392,53.3L1440,32L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
        animation-delay: -5s;
      }
    }
  }
}

// 动画关键帧
@keyframes floatNote {
  0%, 100% {
    transform: translateY(0) rotate(-10deg);
  }
  50% {
    transform: translateY(-20px) rotate(10deg);
  }
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

@keyframes wave {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

// 响应式设计
@media screen and (max-width: 768px) {
  .not-found {
    .main-content {
      h1 {
        font-size: 80px;
      }

      .message {
        font-size: 20px;
      }

      .vinyl-record {
        width: 150px;
        height: 150px;

        .record-center {
          width: 40px;
          height: 40px;
          border-width: 6px;
        }
      }
    }
  }
}
</style>