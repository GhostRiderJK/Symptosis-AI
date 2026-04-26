import csv
import os
import random
from datetime import datetime, timedelta

OUTPUT_PATH = os.path.join(os.path.dirname(__file__), "dataset.csv")
RESOURCE_TYPES = ["wearable", "mobile", "kiosk", "tablet"]
SENSOR_IDS = [f"SNS-{index:03d}" for index in range(1, 21)]


def classify(severity, frequency, duration, system_tier, peak_hours):
    if severity >= 7 and frequency >= 6 and duration >= 30 and system_tier == 3 and peak_hours == 1:
        return "HIGH"
    if severity <= 4 and frequency <= 3 and duration <= 20 and system_tier == 1:
        return "LOW"
    return "MEDIUM"


def generate_row(index):
    now = datetime.now()
    timestamp = now - timedelta(days=random.randint(0, 29), hours=random.randint(0, 23), minutes=random.randint(0, 59))
    severity = random.randint(1, 10)
    frequency = random.randint(1, 10)
    duration = random.randint(5, 90)
    peak_hours = random.randint(0, 1)

    if random.random() < 0.3:
        severity = random.randint(7, 10)
        frequency = random.randint(6, 10)
        duration = random.randint(30, 90)
        system_tier = 3
        peak_hours = 1
    elif random.random() < 0.35:
        severity = random.randint(1, 4)
        frequency = random.randint(1, 3)
        duration = random.randint(5, 20)
        system_tier = 1
    else:
        system_tier = random.randint(1, 3)

    risk_category = classify(severity, frequency, duration, system_tier, peak_hours)
    return {
        "event_id": f"EVT-{index:05d}",
        "timestamp": timestamp.strftime("%Y-%m-%d %H:%M:%S"),
        "incident_severity": severity,
        "frequency_rate": frequency,
        "avg_duration_minutes": duration,
        "system_tier": system_tier,
        "resource_type": random.choice(RESOURCE_TYPES),
        "is_peak_hours": peak_hours,
        "source_sensor_id": random.choice(SENSOR_IDS),
        "ingestion_latency_ms": random.randint(100, 1200),
        "risk_category": risk_category,
    }


def main(rows=1200):
    fieldnames = [
        "event_id",
        "timestamp",
        "incident_severity",
        "frequency_rate",
        "avg_duration_minutes",
        "system_tier",
        "resource_type",
        "is_peak_hours",
        "source_sensor_id",
        "ingestion_latency_ms",
        "risk_category",
    ]
    with open(OUTPUT_PATH, "w", newline="", encoding="utf-8") as csv_file:
        writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
        writer.writeheader()
        for index in range(1, rows + 1):
            writer.writerow(generate_row(index))
    print(f"Generated {rows} rows at {OUTPUT_PATH}")


if __name__ == "__main__":
    main()
