import os
import joblib
import pandas as pd

MODEL_PATH = os.path.join(os.path.dirname(__file__), "model.pkl")


def load_model_bundle():
    if not os.path.exists(MODEL_PATH):
        raise FileNotFoundError("model.pkl not found. Run train.py first.")
    return joblib.load(MODEL_PATH)


def predict_risk(payload: dict) -> dict:
    bundle = load_model_bundle()
    model = bundle["model"]
    frame = pd.DataFrame([payload])
    probabilities = model.predict_proba(frame)[0]
    prediction = model.predict(frame)[0]
    confidence = float(max(probabilities))
    return {
        "riskCategory": str(prediction),
        "confidence": round(confidence, 4),
    }
