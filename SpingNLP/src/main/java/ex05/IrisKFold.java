package ex05;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//교차검증 방식
//2가지를 비교해보자
public class IrisKFold {
	Instances iris;
	Classifier model; //Classifier 알고리즘 모델의 부모유형타입
	String path="C:\\Weka-3-9\\data\\iris.arff"; //데이터 경로
	public IrisKFold() {
		model = new NaiveBayes();
	}
	
	//hold-out : 훈련세트 + 테스트세트 분리하여 검증
	public double split(int seed) throws Exception{
		DataSource ds=new DataSource(path);
		iris=ds.getDataSet();//원본데이터 로드
		//원본데이터를 훈련데이터와 테스트데이터로 분리하는 작업
		int percent=80; //훈련데이터 비율 80% 테스트 20%
		
		int trainSize=Math.round(iris.numInstances()*percent/100); //80%훈련데이터 
		//numInstances로 iris의 전체 데이터수가져옴
		int testSize=iris.numInstances()-trainSize; //20%테스트데이터
		
		//데이터가 편향되는걸 방지하기위해 랜덤으로 배치
		iris.randomize(new Random(seed));
		
		Instances train = new Instances(iris,0,trainSize); //훈련데이터
		Instances test = new Instances(iris,trainSize,testSize); //테스트데이터
		
		train.setClassIndex(train.numAttributes()-1);
		test.setClassIndex(test.numAttributes()-1);
		
		//hold-out
		Evaluation eval=new Evaluation(train);
		model=new NaiveBayes();
		//학습
		model.buildClassifier(train);//model을 학습
		
		//평가
		eval.evaluateModel(model, test); //테스트데이터로 모델을 평가한다
		
		double crr=eval.pctCorrect();
		System.out.println("정분류율: "+crr);
		return crr;
	}
	//k-fold crossvalidate: k개로 분리하여 훈련세트+테스트세트로 구성한 뒤 k번 반복돌면서 평균값을 검증결과로 사용한다.

	public double crossValidation(int seed) throws Exception{
		int numfolds=5;
	
		iris=(new DataSource(path)).getDataSet();
		iris.randomize(new Random(seed));
		
		Instances train=iris.trainCV(numfolds, 0, new Random(1));
		Instances test=iris.testCV(numfolds,0);
		
		//class assigner
		train.setClassIndex(train.numAttributes()-1);
		test.setClassIndex(test.numAttributes()-1);
		
		Evaluation eval=new Evaluation(train);
		model=new NaiveBayes();
		
		eval.crossValidateModel(model, train, numfolds, new Random(seed));
		
		//학습
		model.buildClassifier(train);//model을 학습
		
		//평가
		eval.evaluateModel(model, test); //테스트데이터로 모델을 평가한다
		double val=eval.pctCorrect();
		System.out.println("정분류율: "+val);
		
		return val;
	}
	//집계해주는 함수 (평균, 표준편차)
	public void aggregateValue(double[] sum) {
		AggregateSummaryStatistics aggr = new AggregateSummaryStatistics();
		SummaryStatistics sumObj=aggr.createContributingStatistics();
		for(double v : sum) {
			sumObj.addValue(v);
		}
		System.out.println("평균값: "+aggr.getMean());
		System.out.println("표쥰편차: "+aggr.getStandardDeviation());
	}
	public static void main(String[] args) throws Exception {
		IrisKFold iriskfold = new IrisKFold();
		double[] result = new double[5];
		System.out.println("80% split .... Hold out--------");
		result[0]=iriskfold.split(1);
		result[1]=iriskfold.split(3);
		result[2]=iriskfold.split(5);
		result[3]=iriskfold.split(7);
		result[4]=iriskfold.split(9);
		iriskfold.aggregateValue(result); //집계처리
		
		double[] result2 = new double[5];
		System.out.println("cross validation-교차검증------");
		result2[0]=iriskfold.crossValidation(1);
		result2[1]=iriskfold.crossValidation(2);
		result2[2]=iriskfold.crossValidation(3);
		result2[3]=iriskfold.crossValidation(4);
		result2[4]=iriskfold.crossValidation(5);
		iriskfold.aggregateValue(result2);
		
		
	}
}
