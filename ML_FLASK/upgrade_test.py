from flask import Flask, request
import numpy as np
from PIL import Image
import os, glob, numpy as np
from tensorflow.keras.models import load_model
app = Flask(__name__)

@app.route('/', methods = ['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        X=[]
        class_names = ["김치찌개", "라면", "양념게장", "제육볶음","된장찌개",
             "돈까스","김치볶음밥","비빔밥","삼겹살","스파게티"]
        f = request.files['image']
        image_w = 64
        image_h = 64
        #저장할 경로 + 파일명
        f.save(f.filename)
        img = Image.open(f.filename)
        img = img.convert("RGB")
        img = img.resize((image_w,image_h))
        data = np.asarray(img)
        X.append(data)
        X = np.array(X)
        model = load_model('./upgrade_model/upgrade_multi_img_classification.model')
        prediction = model.predict(X)
        np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
        pre_ans = prediction.argmax()
        pre_ans_str = ''
        if pre_ans == 0: pre_ans_str = class_names[0]
        elif pre_ans == 1: pre_ans_str = class_names[1]
        elif pre_ans == 2: pre_ans_str = class_names[2]
        elif pre_ans == 3: pre_ans_str = class_names[3]
        elif pre_ans == 4: pre_ans_str = class_names[4]
        elif pre_ans == 5: pre_ans_str = class_names[5]
        elif pre_ans == 6: pre_ans_str = class_names[6]
        elif pre_ans == 7: pre_ans_str = class_names[7]
        elif pre_ans == 8: pre_ans_str = class_names[8]
        else: pre_ans_str = class_names[9]
        if prediction[0][0] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][1] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][2] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][3] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][4] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][5] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][6] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][7] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][8] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        if prediction[0][9] >= 0.8: print("이미지는 "+pre_ans_str+"로 추정됩니다.")
        return pre_ans_str

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
