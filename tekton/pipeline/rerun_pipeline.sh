#!/bin/bash

# Delete old resources
kubectl delete deployment mysql
kubectl delete pipelines ci-pipeline
kubectl delete pipelineruns ci-pipeline-run
kubectl delete pvc --all
kubectl delete pv --all

# Reapply the pipeline and pipeline run
kubectl apply -f pipeline2.yml
kubectl apply -f pipeline2-run.yml

# Follow the logs
tkn pipelinerun logs ci-pipeline-run --follow
